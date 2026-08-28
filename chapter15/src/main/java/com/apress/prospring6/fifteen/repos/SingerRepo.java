package com.apress.prospring6.fifteen.repos;

import com.apress.prospring6.fifteen.entities.Singer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SingerRepo extends JpaRepository<Singer, Long> {
}
