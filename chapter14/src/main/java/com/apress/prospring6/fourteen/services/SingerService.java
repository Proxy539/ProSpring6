package com.apress.prospring6.fourteen.services;

import com.apress.prospring6.fourteen.entities.Singer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SingerService {

    List<Singer> findAll();

    Singer findById(Long id);

    Singer save(Singer singer);

    void delete(Long id);

    Page<Singer> findAllByPage(Pageable pageable);

    List<Singer> getByCriteriaDto(CriteriaDto criteria) throws InvalidCriteriaException;
}
