package com.apress.prospring6.fifteen.controllers;

import com.apress.prospring6.fifteen.entities.Singer;
import com.apress.prospring6.fifteen.services.SingerService;
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
@RequestMapping(path = "/singer3")
public class Singer3Controller {

    final Logger LOGGER = LoggerFactory.getLogger(Singer3Controller.class);

    private final SingerService singerService;

    public Singer3Controller(SingerService singerService) {
        this.singerService = singerService;
    }

    @GetMapping("/")
    public List<Singer> all() {
        return singerService.findAll();
    }

    @GetMapping("/{id}")
    public Singer findSingerById(@PathVariable("id") Long id) {
        return singerService.findById(id);
    }

    @PostMapping("/")
    public ResponseEntity<Singer> create(@RequestBody @Valid Singer singer) {
        LOGGER.info("Creating singer: {}", singer);
        try {
            var saved = singerService.save(singer);
            LOGGER.info("Singer created successfully with info: {}", saved);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException dive) {
            LOGGER.debug("Could not create singer.", dive);
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public Singer update(@RequestBody Singer singer, @PathVariable("id") Long id) {
        LOGGER.info("Updating singer: {}", singer);
        var updated = singerService.update(id, singer);
        LOGGER.info("Singer updated successfully with info: {}", updated);
        return updated;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        LOGGER.info("Deleting singer with id: {}", id);
        singerService.delete(id);
        LOGGER.info("Singer deleted successfully");
    }
}
