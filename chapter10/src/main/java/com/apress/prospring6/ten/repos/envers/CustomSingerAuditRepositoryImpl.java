package com.apress.prospring6.ten.repos.envers;

import com.apress.prospring6.ten.entities.SingerAudit;
import com.apress.prospring6.ten.repos.CustomSingerAuditRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.envers.AuditReaderFactory;

import java.util.Optional;

public class CustomSingerAuditRepositoryImpl implements CustomSingerAuditRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<SingerAudit> findAuditByIdAndRevision(Long id, int revision) {
        var auditReader = AuditReaderFactory.get(entityManager);
        var result = auditReader.find(SingerAudit.class, id, revision);

        if (result != null) {
            return Optional.of(result);
        }

        return Optional.empty();
    }
}
