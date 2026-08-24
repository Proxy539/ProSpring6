package com.apress.prospring6.ten.repos;

import com.apress.prospring6.ten.entities.Singer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface SingerRepository extends CrudRepository<Singer, Long> {

    Iterable<Singer> findByFirstName(String firstName);

    Iterable<Singer> findByFirstNameAndLastName(String firstName, String lastName);

    @Modifying(clearAutomatically = true)
    @Query("update Singer s set s.firstName = ?1 where s.id = ?2")
    int setFirstNameFor(String firstName, Long id);

}
