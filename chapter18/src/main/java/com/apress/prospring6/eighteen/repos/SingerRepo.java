package com.apress.prospring6.eighteen.repos;

import com.apress.prospring6.eighteen.entities.Singer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SingerRepo extends JpaRepository<Singer, Long> {

    @Query("select s from Singer s where s.firstName = :firstName and s.lastName = :lastName")
    List<Singer> findByFirstNameAndLastName(@Param("firstName") String firstName, @Param("lastName") String lastName);
}
