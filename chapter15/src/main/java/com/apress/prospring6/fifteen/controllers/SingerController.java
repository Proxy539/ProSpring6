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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "singer")
public class SingerController {

    final Logger logger = LoggerFactory.getLogger(SingerController.class);

    private final SingerRepo singerRepo;

    public SingerController(SingerRepo singerRepo) {
        this.singerRepo = singerRepo;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/")
    public List<Singer> all() {
        return singerRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Singer> findSingerById(@PathVariable("id") Long id) {
        return singerRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Singer()));
    }

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody @Valid Singer singer) {
        logger.info("Creating singer: " + singer);
        try {
            var saved = singerRepo.save(singer);
            logger.info("Singer created successfully with info: " + saved);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException dive) {
            logger.debug("Could not create singer.", dive);
            return ResponseEntity.badRequest().body(dive.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public void update(@RequestBody Singer singer, @PathVariable("id") Long id) {
        logger.info("Update singer: {}", singer);
        var fromDb = singerRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Singer does not exists"));

        fromDb.setFirstName(singer.getFirstName());
        fromDb.setLastName(singer.getLastName());
        fromDb.setBirthDate(singer.getBirthDate());

        singerRepo.save(fromDb);

        logger.info("Singer updated successfully with info: " + fromDb);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        logger.info("Deleting singer with id: " + id);
        singerRepo.deleteById(id);
        logger.info("Singer deleted successfully");
    }
}
