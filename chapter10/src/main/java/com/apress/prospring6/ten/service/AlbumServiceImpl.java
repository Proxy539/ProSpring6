package com.apress.prospring6.ten.service;

import com.apress.prospring6.ten.entities.Album;
import com.apress.prospring6.ten.entities.Singer;
import com.apress.prospring6.ten.repos.AlbumRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
@Transactional(readOnly = true)
public class AlbumServiceImpl implements AlbumService {

    private final AlbumRepository albumRepository;

    public AlbumServiceImpl(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    @Override
    public Stream<Album> findBySinger(Singer singer) {
        return StreamSupport.stream(albumRepository.findBySinger(singer)
                .spliterator(), false);
    }

    @Override
    public Stream<Album> findWithReleaseDateGreaterThan(LocalDate rd) {
        return StreamSupport.stream(albumRepository.findWithReleaseDateGreaterThan(rd)
                .spliterator(), false);
    }


}
