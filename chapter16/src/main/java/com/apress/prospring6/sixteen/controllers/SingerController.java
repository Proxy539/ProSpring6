package com.apress.prospring6.sixteen.controllers;

import com.apress.prospring6.sixteen.entities.Singer;
import com.apress.prospring6.sixteen.repos.SingeRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/singer")
public class SingerController {

    final Logger LOGGER = LoggerFactory.getLogger(SingerController.class);

    private final SingeRepo singeRepo;

    public SingerController(SingeRepo singeRepo) {
        this.singeRepo = singeRepo;
    }

    @GetMapping("/")
    public List<Singer> all() {
        return singeRepo.findAll();
    }

    @GetMapping("/{id}")
    public Singer findSingerById(@PathVariable Long id) {
        return singeRepo.findById(id).orElse(null);
    }
}
