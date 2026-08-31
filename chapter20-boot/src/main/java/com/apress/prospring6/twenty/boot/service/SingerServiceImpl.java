package com.apress.prospring6.twenty.boot.service;

import com.apress.prospring6.twenty.boot.model.Singer;
import com.apress.prospring6.twenty.boot.problem.SaveException;
import com.apress.prospring6.twenty.boot.repo.SingerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Transactional
@Service
public class SingerServiceImpl implements SingerService {

    private final SingerRepo singerRepo;

    @Override
    public Flux<Singer> findAll() {
        return singerRepo.findAll();
    }

    @Override
    public Mono<Singer> findById(Long id) {
        return singerRepo.findById(id);
    }

    @Override
    public Mono<Singer> findByFirstNameAndLastName(String firstName, String lastName) {
        return singerRepo.findByFirstNameAndLastName(firstName, lastName);
    }

    @Override
    public Flux<Singer> findByFirstName(String firstName) {
        return singerRepo.findByFirstName(firstName);
    }

    @Override
    public Mono<Singer> save(Singer singer) {
        return singerRepo.save(singer)
                .onErrorMap(error -> new SaveException("Could Not Save Singer " + singer, error));
    }

    @Override
    public Mono<Singer> update(Long id, Singer updateData) {
        return singerRepo.findById(id)
                .flatMap(original -> {
                    original.setFirstName(updateData.getFirstName());
                    original.setLastName(updateData.getLastName());
                    original.setBirthDate(updateData.getBirthDate());
                    return singerRepo.save(original)
                            .onErrorMap(error -> new SaveException("Could Not Update Singer " +
                                    updateData, error));
                });
    }

    @Override
    public Mono<Void> delete(Long id) {
        return singerRepo.deleteById(id);
    }

    @Override
    public Flux<Singer> findByCriteriaDto(CriteriaDto criteria) {
        var fieldName = FieldGroup.getField(criteria.getFieldName().toUpperCase());
        if ("*".equals(criteria.getFieldValue())) {
            return singerRepo.findAll();
        }

        return switch (fieldName) {
            case FIRSTNAME -> "*".equals(criteria.getFieldValue()) ? singerRepo.findAll() : singerRepo.findByFirstName(criteria.getFieldValue());
            case LASTNAME -> "*".equals(criteria.getFieldValue()) ? singerRepo.findAll() : singerRepo.findByLastName(criteria.getFieldValue());
            case BIRTHDATE -> "*".equals(criteria.getFieldValue()) ? singerRepo.findAll() : singerRepo.findByBirthDate(LocalDate.parse(criteria.getFieldValue(),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        };
    }
}
