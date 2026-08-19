package com.apress.prospring6.seven.base.dao;

import com.apress.prospring6.seven.base.entities.Album;
import com.apress.prospring6.seven.base.entities.Instrument;
import com.apress.prospring6.seven.base.entities.Singer;
import org.hibernate.SessionFactory;
import org.hibernate.query.specification.ProjectionSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Transactional(readOnly = true)
@Repository("singerDao")
public class SingerDaoImpl implements SingerDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(SingerDaoImpl.class);
    private SessionFactory sessionFactory;

    private static final String ALL_SELECT = """
            SELECT DISTINCT s.first_name, s.last_name, a.title, a.RELEASE_DATE,
            i.INSTRUMENT_ID
            from SINGER s 
            inner join ALBUM a on s.id = a.singer_id
            inner join SINGER_INSTRUMENT si on s.ID = si.SINGER_ID
            inner join INSTRUMENT i on si.INSTRUMENT_ID = i.INSTRUMENT_ID
            where s.first_NAME = :firstName and s.LAST_NAME = :lastName
            """;


    public SingerDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Singer> findAll() {
        // left join fetch pulls albums/instruments into the same session so they're
        // already initialized by the time the caller (outside the transaction) reads
        // them - otherwise Singer.toString() triggers a LazyInitializationException.
        return sessionFactory.getCurrentSession()
                .createQuery("select distinct s from Singer s left join fetch s.albums left join fetch s.instruments", Singer.class)
                .list();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Singer> findAllWithAlbum() {
        return sessionFactory.getCurrentSession().getNamedQuery("Singer.findAllWithAlbum")
                .list();
    }

    @Override
    public Singer findById(Long id) {
        return (Singer) sessionFactory.getCurrentSession()
                .getNamedQuery("Singer.findById")
                .setParameter("id", id)
                .uniqueResult();
    }

    @Transactional
    @Override
    public Singer save(Singer singer) {
        var session = sessionFactory.getCurrentSession();
        if (singer.getId() == null) {
            session.persist(singer);
        } else {
            // the singer passed in is detached (e.g. loaded in an earlier transaction),
            // so merge its state onto the managed entity instead of persisting it directly.
            singer = session.merge(singer);
        }
        LOGGER.debug("Singer saved with id: " + singer.getId());
        return singer;
    }

    @Transactional
    @Override
    public void delete(Singer singer) {
        var session = sessionFactory.getCurrentSession();
        // singer was loaded (and detached) in a prior transaction; reattach it before
        // removing, otherwise Hibernate can't compute a collection snapshot for the
        // cascaded orphan-delete of albums.
        session.remove(session.merge(singer));
        LOGGER.info("Singer deleted with id: " + singer.getId());
    }

    @Override
    public Singer findAllDetails(String firstName, String lastName) {
        List<Object[]> results = sessionFactory.getCurrentSession()
                .createNativeQuery(ALL_SELECT)
                .setParameter("firstName", firstName)
                .setParameter("lastName", lastName)
                .list();

        var singer = new Singer();

        for (Object[] item : results) {
            if (singer.getFirstName() == null && singer.getLastName() == null) {
                singer.setFirstName((String) item[0]);
                singer.setLastName((String) item[1]);
            }
            var album = new Album();
            album.setTitle((String) item[2]);
            album.setReleaseDate(((Date) item[3]).toLocalDate());
            singer.addAlbum(album);

            var instrument = new Instrument();
            instrument.setInstrumentId((String) item[4]);
            singer.getInstruments().add(instrument);
        }

        return singer;
    }

//    @Transactional(readOnly = true)
//    @Override
//    public Set<String> findAllNamesByProjection() {
//        sessionFactory.getCurrentSession().getCriteriaBuilder().createCriteria(Singer.class);
//        Projection fnProjection = Projections.property("firstName");
//        Projection lnProjection = Projections.property("lastName");
//
//        ProjectionList pList = Projections.projectionList();
//        pList.add(fnProjection);
//        pList.add(lnProjection);
//        criteria.setProjection(pList);
//
//        List<Object[]> projResult = criteria.list();
//        return projResult.stream().map(o -> o[0] + o[1]).collect(Collectors.toSet());
//
//    }

//    @Transactional(readOnly = true)
//    @Override
//    public String findFirstNameById(Long id) {
//        return sessionFactory.getCurrentSession().createQuery("select getfirstnamebyid(?)")
//                .setParameter(1, id)
//                .getSingleResult()
//                .toString();
//    }
}
