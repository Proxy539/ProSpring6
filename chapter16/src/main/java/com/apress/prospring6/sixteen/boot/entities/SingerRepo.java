package com.apress.prospring6.sixteen.boot.entities;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SingerRepo extends JpaRepository<Singer, Long>, JpaSpecificationExecutor<Singer> {
}
