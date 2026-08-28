package com.apress.prospring6.fourteen.services;

import com.apress.prospring6.fourteen.entities.Singer;
import com.apress.prospring6.fourteen.problem.NotFoundException;
import com.apress.prospring6.fourteen.repos.SingerRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    public Singer findById(Long id) {
        return singerRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Singer not found for id: " + id));
    }

    @Override
    public Singer save(Singer singer) {
        return singerRepo.save(singer);
    }

    @Override
    public void delete(Long id) {
        singerRepo.deleteById(id);
    }

    @Override
    public Page<Singer> findAllByPage(Pageable pageable) {
        return singerRepo.findAll(pageable);
    }

    @Override
    public List<Singer> getByCriteriaDto(CriteriaDto criteria) throws InvalidCriteriaException {
        if (criteria == null) {
            throw new InvalidCriteriaException("criteria must not be null");
        }

        if (criteria.getFirstName() != null) {
            return toList(singerRepo.findByFirstNameLike(criteria.getFirstName()));
        }
        if (criteria.getLastName() != null) {
            return toList(singerRepo.findByLastNameLike(criteria.getLastName()));
        }
        if (criteria.getBirthDate() != null) {
            return toList(singerRepo.findByBirthDate(criteria.getBirthDate()));
        }

        throw new InvalidCriteriaException("criteria must specify at least one search field");
    }

    private static List<Singer> toList(Iterable<Singer> singers) {
        var result = new ArrayList<Singer>();
        singers.forEach(result::add);
        return result;
    }
}
