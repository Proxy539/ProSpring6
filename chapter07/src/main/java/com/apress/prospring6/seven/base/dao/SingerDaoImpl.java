package com.apress.prospring6.seven.base.dao;

import com.apress.prospring6.seven.base.entities.Singer;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional(readOnly = true)
@Repository("singerDao")
public class SingerDaoImpl implements SingerDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(SingerDaoImpl.class);
    private SessionFactory sessionFactory;

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

    @Override
    public Singer save(Singer singer) {
        return null;
    }

    @Override
    public void delete(Singer singer) {

    }
}
