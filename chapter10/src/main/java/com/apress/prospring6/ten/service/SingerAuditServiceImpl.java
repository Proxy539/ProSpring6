package com.apress.prospring6.ten.service;

import com.apress.prospring6.ten.entities.SingerAudit;
import com.apress.prospring6.ten.repos.SingerAuditRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

@Service("singerAuditService")
@Transactional
public class SingerAuditServiceImpl implements SingerAuditService {

    private final SingerAuditRepository singerAuditRepository;

    public SingerAuditServiceImpl(SingerAuditRepository singerAuditRepository) {
        this.singerAuditRepository = singerAuditRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public SingerAudit findAuditByRevision(Long id, int revision) {
        return singerAuditRepository.findAuditByIdAndRevision(id, revision).orElse(null);
    }

    @Override
    public Stream<SingerAudit> findAll() {
        return Stream.empty();
    }

    @Override
    public SingerAudit findById(Long id) {
        return null;
    }

    @Override
    public SingerAudit save(SingerAudit singer) {
        return null;
    }
}
