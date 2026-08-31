package com.apress.prospring6.twenty.boot.controller;

import com.apress.prospring6.twenty.boot.model.Singer;
import com.apress.prospring6.twenty.boot.service.SingerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/reactive/singer")
public class ReactiveSingerController {

    private final SingerService singerService;

    @GetMapping(path = {"/", "/"})
    public Flux<Singer> list() {
        return singerService.findAll();
    }

    @GetMapping(path = "/{id}")
    public Mono<ResponseEntity<Singer>> findById(@PathVariable Long id) {
        return singerService.findById(id)
                .map(s -> ResponseEntity.ok().body(s))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Singer> create(@RequestBody Singer singer) {
        return singerService.save(singer);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Singer>> updateById(@PathVariable Long id, @RequestBody Singer singer) {
        return singerService.update(id, singer)
                .map(s -> ResponseEntity.ok().body(s))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteById(@PathVariable Long id) {
        return singerService.delete(id)
                .then(Mono.fromCallable(() -> ResponseEntity.noContent().<Void>build()))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping(params = {"name"})
    public Flux<Singer> searchSingers(@RequestParam("name") String name) {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("Missing request parameter 'name'");
        }

        return singerService.findByFirstName(name);
    }

    @GetMapping(params = {"fn", "ln"})
    public Mono<Singer> searchSinger(@RequestParam("fn") String fn, @RequestParam("ln") String ln) {
        if ((StringUtils.isBlank(fn) || StringUtils.isBlank(ln))) {
            throw new IllegalArgumentException("Missing request parameter, on of {'fn', 'ln'}");
        }
        return singerService.findByFirstNameAndLastName(fn, ln);
    }
}
