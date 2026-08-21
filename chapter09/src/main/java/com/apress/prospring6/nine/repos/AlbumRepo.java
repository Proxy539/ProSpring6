package com.apress.prospring6.nine.repos;

import com.apress.prospring6.nine.entities.Album;
import com.apress.prospring6.nine.entities.Singer;
import com.apress.prospring6.nine.ex.TitleTooLongException;

import java.util.Set;
import java.util.stream.Stream;

public interface AlbumRepo {

    Stream<Album> findBySinger(Singer singer);

    Set<Album> save(Set<Album> albums) throws TitleTooLongException;

    Album save(Album album) throws TitleTooLongException;
}
