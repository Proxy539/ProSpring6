package com.apress.prospring6.ten;

import com.apress.prospring6.ten.config.AuditCfg;
import com.apress.prospring6.ten.config.DataJpaCfg;
import com.apress.prospring6.ten.entities.SingerAudit;
import com.apress.prospring6.ten.service.SingerAuditService;
import jakarta.annotation.PostConstruct;
import org.hibernate.cfg.Environment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
@Sql({"classpath:testcontainers/audit/drop-schema.sql", "classpath:testcontainers/audit/create-schema.sql"})
@SpringJUnitConfig(classes = {AuditServiceTest.TestContainersConfig.class})
public class AuditServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuditServiceTest.class);

    @Autowired
    SingerAuditService auditService;

    @BeforeEach
    void setUp() {
        var singer = new SingerAudit();
        singer.setFirstName("BB");
        singer.setLastName("King");
        singer.setBirthDate(LocalDate.of(1940, 8, 16));
        auditService.save(singer);
    }

    @Test
    void testFindById() {
        var singer = auditService.findAll().findFirst().orElse(null);

        assertAll("auditFindByIdTest",
                () -> assertNotNull(singer),
                () -> assertTrue(singer.getCreatedBy().isPresent()),
                () -> assertTrue(singer.getLastModifiedBy().isPresent()),
                () -> assertNotNull(singer.getCreatedDate()),
                () -> assertNotNull(singer.getLastModifiedDate()));

        LOGGER.info(">> created record: {}", singer);
    }

    @Test
    void testUpdate() {
        var singer = auditService.findAll().findFirst().orElse(null);
        assertNotNull(singer);

        singer.setFirstName("Riley B.");
        var updated = auditService.save(singer);

        assertAll("auditUpdateTest",
                () -> assertEquals("Riley B.", updated.getFirstName()),
                () -> assertTrue(updated.getLastModifiedBy().isPresent()),
                () -> assertNotEquals(updated.getCreatedBy().orElse(null), updated.getLastModifiedBy().orElse(null)));

        LOGGER.info(">> updated record: {}", updated);
    }

    @Configurable
    @Import({DataJpaCfg.class, AuditCfg.class})
    public static class TestContainersConfig {
        @Autowired
        Properties jpaProperties;

        @PostConstruct
        public void initialize() {
            jpaProperties.put(Environment.FORMAT_SQL, true);
            jpaProperties.put(Environment.USE_SQL_COMMENTS, true);
            jpaProperties.put(Environment.SHOW_SQL, true);
            jpaProperties.put(Environment.STATEMENT_BATCH_SIZE, 30);
        }
    }
}
