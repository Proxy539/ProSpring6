package com.apress.prospring6.ten.repos;

import com.apress.prospring6.ten.entities.Album;
import com.apress.prospring6.ten.entities.Singer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface AlbumRepository extends JpaRepository<Album, Long> {

    Iterable<Album> findBySinger(Singer singer);
    Iterable<Album> findWithReleaseDateGreaterThan(LocalDate rd);

    @Query("select a from Album a where a.title like %:title%")
    Iterable<Album> findByTitle(@Param("title") String t);
}
