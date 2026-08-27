package com.apress.prospring6.fourteen.repos;

import com.apress.prospring6.fourteen.entities.Singer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface SingerRepo extends JpaRepository<Singer, Long> {

    @Query("select s from Singer s where s.firstName = :fn")
    Iterable<Singer> findByFirstName(@Param("fn") String firstName);

    @Query("select s from Singer s where s.firstName like %?1%")
    Iterable<Singer> findByFirstNameLike(String firstName);

    @Query("select s from Singer s where s.lastName = :ln")
    Iterable<Singer> findByLastName(@Param("ln") String lastName);

    @Query("select s from Singer s where s.lastName like %?1%")
    Iterable<Singer> findByLastNameLike(String lastName);

    @Query("select s from Singer s where s.birthDate = :date")
    Iterable<Singer> findByBirthDate(@Param("date")LocalDate date);

}
