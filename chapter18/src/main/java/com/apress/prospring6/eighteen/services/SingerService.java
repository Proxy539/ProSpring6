package com.apress.prospring6.eighteen.services;

import com.apress.prospring6.eighteen.entities.Singer;

import java.util.List;

public interface SingerService {

    List<Singer> findAll();

    List<Singer> findByFirstNameAndLastName(String firstName, String lastName);

    Singer save(Singer singer);

    void delete(Long id);
}
