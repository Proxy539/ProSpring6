package com.apress.prospring6.nine.services;

import com.apress.prospring6.nine.entities.Album;
import com.apress.prospring6.nine.entities.Singer;
import com.apress.prospring6.nine.ex.TitleTooLongException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public interface AllService {

    Optional<Singer> findByIdWithAlbums(Long id);

    Stream<Singer> findAllWithAlbums();

    void update(Singer singer);

    Long countSingers();

    @Transactional(rollbackFor = TitleTooLongException.class)
    void saveSingerWithAlbums(Singer s, Set<Album> albums) throws TitleTooLongException;
}
