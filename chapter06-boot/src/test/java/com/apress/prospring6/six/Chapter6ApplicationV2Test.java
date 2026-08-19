package com.apress.prospring6.six;

import com.apress.prospring6.six.repo.SingerRepo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("testcontainers")
@Testcontainers
@Sql(value = "classpath:testcontainers/create-schema.sql",
config = @SqlConfig(encoding = "utf-8", separator = ";", commentPrefix = "--"),
executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@SpringBootTest(classes = Chapter06Application.class)
public class Chapter6ApplicationV2Test {

    private static final Logger LOGGER = LoggerFactory.getLogger(Chapter6ApplicationV2Test.class);

    @Autowired
    SingerRepo singerRepo;

    @Test
    @DisplayName("should return all singers")
    void testFindAllWithJdbcTemplate() {
        var singers = singerRepo.findAll().stream().toList();
        assertEquals(3, singers.size());
        singers.forEach(singer -> LOGGER.info(singer.toString()));
    }

    @Test
    @DisplayName("find singer by name")
    @Sql({"classpath:testcontainers/stored-function.sql"})
    void testStoredFunction() {
        var firstName = singerRepo.findFirstNameById(2L).orElse(null);
        assertEquals("Ben", firstName);
    }
}
