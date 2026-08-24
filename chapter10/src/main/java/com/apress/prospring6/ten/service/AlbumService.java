package com.apress.prospring6.ten.service;

import com.apress.prospring6.ten.entities.Album;
import com.apress.prospring6.ten.entities.Singer;

import java.time.LocalDate;
import java.util.stream.Stream;

public interface AlbumService {
    Stream<Album> findBySinger(Singer singer);

    Stream<Album> findWithReleaseDateGreaterThan(LocalDate rd);
}
