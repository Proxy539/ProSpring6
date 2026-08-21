package com.apress.prospring6.nine.repos;

import com.apress.prospring6.nine.entities.Singer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public class SingerRepoImpl implements SingerRepo {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Stream<Singer> findAll() {
        return em.createNamedQuery(Singer.FIND_ALL, Singer.class)
                .getResultList()
                .stream();
    }

    @Override
    public Optional<Singer> findById(Long id) {
        return Optional.ofNullable(em.find(Singer.class, id));
    }

    @Override
    public Long countAllSingers() {
        return em.createNamedQuery(Singer.COUNT_ALL, Long.class).getSingleResult();
    }

    @Override
    public Singer save(Singer singer) {
        if (singer.getId() == null) {
            em.persist(singer);
            return singer;
        } else {
            return em.merge(singer);
        }
    }
}
