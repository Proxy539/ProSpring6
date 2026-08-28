package com.apress.prospring6.sixteen.kotlin.boot

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/singer")
class SingerController (private val service: SingerService) {

    @GetMapping
    fun all(): List<Singer> {
        return service.findAll()
    }

    @GetMapping("/{id}")
    fun findSingerById(@PathVariable id: Long): Singer? {
        return service.findById(id)
    }

    @PostMapping
    fun create(@RequestBody @Valid singer: Singer): Singer? {
        return service.save(singer)
    }

    @PutMapping("/{id}")
    fun update(@RequestBody @Valid singer: Singer, @PathVariable id: Long): Singer? {
        return service.update(id, singer)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) {
        service.delete(id)
    }
}