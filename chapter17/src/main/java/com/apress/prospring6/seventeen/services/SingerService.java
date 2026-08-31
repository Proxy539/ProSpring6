package com.apress.prospring6.seventeen.services;

import com.apress.prospring6.seventeen.entities.Singer;

public interface SingerService {

    Singer findById(Long id);

    void delete(Long id);
}
