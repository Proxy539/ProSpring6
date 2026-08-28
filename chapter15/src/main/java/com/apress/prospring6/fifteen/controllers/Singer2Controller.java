package com.apress.prospring6.fifteen.controllers;

import com.apress.prospring6.fifteen.entities.Singer;
import com.apress.prospring6.fifteen.repos.SingerRepo;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "singer2")
public class Singer2Controller {

    final Logger LOGGER = LoggerFactory.getLogger(Singer2Controller.class);

    private final SingerRepo singerRepo;

    public Singer2Controller(SingerRepo singerRepo) {
        this.singerRepo = singerRepo;
    }

    @GetMapping("/")
    public ResponseEntity<List<Singer>> all() {
        var singers = singerRepo.findAll();
        if (singers.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(singerRepo.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Singer> findSingerById(@PathVariable("id") Long id) {
        Optional<Singer> fromDb = singerRepo.findById(id);

        return fromDb
                .map(s -> new ResponseEntity<>(s, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/")
    public ResponseEntity<Singer> create(@RequestBody @Valid Singer singer) {
        LOGGER.info("Creating singer: {}", singer);
        try {
            var saved = singerRepo.save(singer);
            LOGGER.info("Singer create successfully with info: {}", saved);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException dive) {
            LOGGER.debug("Could not create singer.", dive);
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@RequestBody Singer singer, @PathVariable("id") Long id) {
        LOGGER.info("Updating singer: " + singer);
        Optional<Singer> fromDb = singerRepo.findById(id);

        return fromDb
                .map(s -> {
                    s.setFirstName(singer.getFirstName());
                    s.setLastName(singer.getLastName());
                    s.setBirthDate(singer.getBirthDate());

                    try {
                        singerRepo.save(s);
                        return ResponseEntity.ok().build();
                    } catch (DataIntegrityViolationException dive) {
                        LOGGER.debug("Could not update singer.", dive);
                        return ResponseEntity.badRequest().build();
                    }
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        LOGGER.info("Deleting singer with id: " + id);

        Optional<Singer> fromDb = singerRepo.findById(id);

        return fromDb
                .map(s -> {
                    singerRepo.deleteById(id);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
