package com.apress.prospring6.six.repo;

import com.apress.prospring6.six.plain.dao.SingerDao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.jdbc.SqlMergeMode;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
@Sql({"classpath:h2/drop-schema.sql", "classpath:h2/creatre-schema.sql"})
@SpringJUnitConfig(classes = {JdbcRepoTest.class, JdbcRepoTest.EmptyEmbeddedJdbcConfig.class})
public class JdbcRepoTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RepoBeanTest.class);

    @Autowired
    SingerRepo singerRepo;

    @Test
    @DisplayName("should return all singers")
    @Sql(value = "classpath:h2/test-data.sql")
    void testFindAllWithMappingSqlQuery() {
        var singers = singerRepo.findAll();
        assertEquals(3, singers.size());
        singers.forEach(singer -> LOGGER.info(singer.toString()));
    }

    @Test
    @DisplayName("should return Chuck Berry")
    @SqlGroup({
            @Sql(statements = "insert into SINGER (first_name, last_name, birth_name) values ('Chuck', 'Berry', '1926-09-18')",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(statements = "delete from SINGER where first_name = 'Chuck'",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    public void testFindByNameWithMappingSqlQuery() {
        var singers = singerRepo.findByFirstName("Chuck");
        assertEquals(1, singers.size());
        LOGGER.info("Result: {}", singers.get(0));
    }

    @Configuration
    public static class EmptyEmbeddedJdbcConfig {
        private static final Logger LOGGER = LoggerFactory.getLogger(EmptyEmbeddedJdbcConfig.class);

        @Bean
        public DataSource dataSource() {
            try {
                var dbBuilder = new EmbeddedDatabaseBuilder();
                return dbBuilder.setType(EmbeddedDatabaseType.H2).setName("prospring")
                        .build();
            } catch (Exception e) {
                LOGGER.error("Embedded DataSource bean cannot be created!", e);
                return null;
            }
        }
    }
}
