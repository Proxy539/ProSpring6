package com.apress.prospring6.twenty.boot.repo;

import com.apress.prospring6.twenty.boot.model.Singer;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public interface SingerRepo extends ReactiveCrudRepository<Singer, Long> {

    @Query("select * from singer where first_name=:fn and last_name=:ln")
    Mono<Singer> findByFirstNameAndLastName(@Param("fn") String firstName, @Param("ln") String lastName);

    @Query("select * from singer where first_name=:fn")
    Flux<Singer> findByFirstName(@Param("fn") String firstName);

    @Query("select * from singer where last_name=:ln")
    Flux<Singer> findByLastName(@Param("ln") String lastName);

    @Query("select * from singer where birth_date=:bd")
    Flux<Singer> findByBirthDate(@Param("bd")LocalDate birthDate);
}
