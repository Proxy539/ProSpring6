package com.apress.prospring6.nine.repos;

import com.apress.prospring6.nine.entities.Album;
import com.apress.prospring6.nine.entities.Singer;
import com.apress.prospring6.nine.ex.TitleTooLongException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

@Repository
public class AlbumRepoImpl implements AlbumRepo {

    @PersistenceContext
    private EntityManager em;

    @Value("#{jpaProperties.get('hibernate.jdbc.batch_size')}")
    private int batchSize;

    @Override
    public Stream<Album> findBySinger(Singer singer) {
        return em.createNamedQuery(Album.FIND_ALL, Album.class)
                .setParameter("singer", singer)
                .getResultList()
                .stream();
    }

    @Override
    public Set<Album> save(Set<Album> albums) throws TitleTooLongException {
        final Set<Album> savedAlbums = new HashSet<>();
        int i = 0;
        for (Album a : albums) {
            savedAlbums.add(save(a));
            i++;

            if (i % batchSize == 0) {
                //flush a batch of inserts and release memory.
                em.flush();
                em.clear();
            }
        }

        return savedAlbums;
    }

    @Override
    public Album save(Album album) throws TitleTooLongException {
        if (50 < album.getTitle().length()) {
            throw new TitleTooLongException("Title " + album.getTitle() + " too long!");
        }
        if (album.getId() == null) {
            em.persist(album);
            return album;
        } else {
            return em.merge(album);
        }
    }
}
