package com.apress.prospring6.eighteen.services;

import com.apress.prospring6.eighteen.entities.Singer;
import com.apress.prospring6.eighteen.repos.SingerRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SingerServiceImpl implements SingerService {

    private final SingerRepo singerRepo;

    public SingerServiceImpl(SingerRepo singerRepo) {
        this.singerRepo = singerRepo;
    }

    @Override
    public List<Singer> findAll() {
        return singerRepo.findAll();
    }

    @Override
    public List<Singer> findByFirstNameAndLastName(String firstName, String lastName) {
        return singerRepo.findByFirstNameAndLastName(firstName, lastName);
    }

    @Override
    public Singer save(Singer singer) {
        return singerRepo.save(singer);
    }

    @Override
    public void delete(Long id) {
        singerRepo.deleteById(id);
    }
}
