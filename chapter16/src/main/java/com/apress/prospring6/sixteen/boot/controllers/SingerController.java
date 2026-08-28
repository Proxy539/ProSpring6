package com.apress.prospring6.sixteen.boot.controllers;

import com.apress.prospring6.sixteen.boot.entities.Award;
import com.apress.prospring6.sixteen.boot.entities.Instrument;
import com.apress.prospring6.sixteen.boot.entities.Singer;
import com.apress.prospring6.sixteen.boot.entities.SingerRepo;
import com.apress.prospring6.sixteen.boot.problem.NotFoundException;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.DataFetchingFieldSelectionSet;
import jakarta.persistence.criteria.Fetch;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Controller
public class SingerController {

    private final SingerRepo singerRepo;

    public SingerController(SingerRepo singerRepo) {
        this.singerRepo = singerRepo;
    }

    @QueryMapping
    public Iterable<Singer> singers(DataFetchingEnvironment environment) {
        DataFetchingFieldSelectionSet s = environment.getSelectionSet();
        if (s.contains("awards") && !s.contains("instruments")) {
            return singerRepo.findAll(fetchAwards());
        } else if (s.contains("awards") && s.contains("instruments")) {
            return singerRepo.findAll(fetchAwards().and(fetchInstruments()));
        } else if (!s.contains("awards") && s.contains("instruments")) {
            return singerRepo.findAll(fetchInstruments());
        } else {
            return singerRepo.findAll();
        }
    }

    @QueryMapping
    public Singer singerById(@Argument Long id, DataFetchingEnvironment environment) {
        Specification<Singer> spec = byId(id);
        DataFetchingFieldSelectionSet s = environment.getSelectionSet();
        if (s.contains("awards") && !s.contains("instruments")) {
            spec = spec.and(fetchAwards());
        } else if (s.contains("awards") && s.contains("instruments")) {
            spec = spec.and(fetchAwards().and(fetchInstruments()));
        } else if (!s.contains("awards") && s.contains("instruments")) {
            spec = spec.and(fetchInstruments());
        }

        return singerRepo.findOne(spec).orElse(null);
    }

    @MutationMapping
    public Singer newSinger(@Argument SingerInput singer) {
        LocalDate date;
        try {
            date = LocalDate.parse(singer.birthDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Bad date format");
        }

        var newSinger = new Singer(null, 0, singer.firstName(), singer.lastName(),
                singer.pseudonym(), singer.genre(), date, null, null);

        return singerRepo.save(newSinger);
    }

    @MutationMapping
    public Singer updateSinger(@Argument Long id, @Argument SingerInput singer) {
        var fromDb = singerRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(Singer.class, id));

        fromDb.setFirstName(singer.firstName());
        fromDb.setLastName(singer.lastName());
        fromDb.setPseudonym(singer.pseudonym());
        fromDb.setGenre(singer.genre());
        LocalDate date;

        try {
            date = LocalDate.parse(singer.birthDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            fromDb.setBirthDate(date);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Bad date format");
        }

        return singerRepo.save(fromDb);
    }

    @MutationMapping
    public Long deleteSinger(@Argument Long id) {
        singerRepo.findById(id).orElseThrow(() -> new NotFoundException(Singer.class, id));
        singerRepo.deleteById(id);
        return id;
    }

    private Specification<Singer> byId(Long id) {
        return (root, query, builder) -> builder.equal(root.get("id"), id);
    }

    private Specification<Singer> fetchAwards() {
        return (root, query, builder) -> {
            Fetch<Singer, Award> f = root.fetch("awards", JoinType.LEFT);
            Join<Singer, Award> join = (Join<Singer, Award>) f;
            return join.getOn();
        };
    }

    private Specification<Singer> fetchInstruments() {
        return ((root, query, builder) -> {
            Fetch<Singer, Instrument> f = root.fetch("instruments", JoinType.LEFT);
            Join<Singer, Instrument> join = (Join<Singer, Instrument>) f;
            return join.getOn();
        });
    }
}
