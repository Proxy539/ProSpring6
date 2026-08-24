package com.apress.prospring6.ten.service;

import com.apress.prospring6.ten.entities.Singer;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

public interface SingerService {
    @Transactional(readOnly = true)
    Stream<Singer> findAll();

    @Transactional(readOnly = true)
    Stream<Singer> findByFirstName(String firstName);

    @Transactional(readOnly = true)
    Stream<Singer> findByFirstNameAndLastName(String firstName, String lastName);

    @Transactional(propagation = Propagation.REQUIRES_NEW, label = "modifying")
    Singer updateFirstName(String firstName, Long id);
}
