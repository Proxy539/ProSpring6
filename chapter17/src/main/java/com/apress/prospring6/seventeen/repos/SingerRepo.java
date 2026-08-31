package com.apress.prospring6.seventeen.repos;

import com.apress.prospring6.seventeen.entities.Singer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SingerRepo extends JpaRepository<Singer, Long> {
}
