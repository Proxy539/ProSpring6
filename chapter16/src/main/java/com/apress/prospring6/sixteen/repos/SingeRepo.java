package com.apress.prospring6.sixteen.repos;

import com.apress.prospring6.sixteen.entities.Singer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SingeRepo extends JpaRepository<Singer, Long> {
}
