package com.apress.prospring6.ten.service;

import com.apress.prospring6.ten.entities.SingerAudit;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

public interface SingerAuditService {
    Stream<SingerAudit> findAll();

    SingerAudit findById(Long id);

    SingerAudit save(SingerAudit singer);

    @Transactional(readOnly = true)
    SingerAudit findAuditByRevision(Long id, int revision);
}
