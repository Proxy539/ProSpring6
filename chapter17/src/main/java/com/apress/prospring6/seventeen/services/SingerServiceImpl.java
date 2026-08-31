package com.apress.prospring6.seventeen.services;

import com.apress.prospring6.seventeen.entities.Singer;
import com.apress.prospring6.seventeen.problem.NotFoundException;
import com.apress.prospring6.seventeen.repos.SingerRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SingerServiceImpl implements SingerService {

    private final SingerRepo singerRepo;

    public SingerServiceImpl(SingerRepo singerRepo) {
        this.singerRepo = singerRepo;
    }

    @Override
    public Singer findById(Long id) {
        return singerRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Singer not found for id: " + id));
    }

    @Override
    public void delete(Long id) {
        singerRepo.deleteById(id);
    }
}
