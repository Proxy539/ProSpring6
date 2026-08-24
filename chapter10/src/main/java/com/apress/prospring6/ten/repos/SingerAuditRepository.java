package com.apress.prospring6.ten.repos;

import com.apress.prospring6.ten.entities.SingerAudit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SingerAuditRepository extends JpaRepository<SingerAudit, Long>, CustomSingerAuditRepository {
}
