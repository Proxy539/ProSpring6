package com.apress.prospring6.seven.base.dao;

import com.apress.prospring6.seven.base.entities.Singer;

import java.util.List;

public interface SingerDao {

    List<Singer> findAll();
    List<Singer> findAllWithAlbum();
    Singer findById(Long id);
    Singer save(Singer singer);
    void delete(Singer singer);

    Singer findAllDetails(String firstName, String lastName);
}
