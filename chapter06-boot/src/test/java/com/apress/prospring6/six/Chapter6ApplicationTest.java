package com.apress.prospring6.six;

import com.apress.prospring6.six.repo.SingerJdbcRepo;
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
import org.springframework.test.context.jdbc.SqlMergeMode;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
@Sql({"classpath:h2/drop-schema.sql", "classpath:h2/create-schema.sql"})
@SpringBootTest
public class Chapter6ApplicationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(Chapter6ApplicationTest.class);

    @Autowired
    SingerRepo singerRepo;

    @Test
    @DisplayName("should return all singers")
    @Sql(value = "classpath:h2/test-data.sql",
    config = @SqlConfig(encoding = "utf-8", separator = ";", commentPrefix = "--"),
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void testFindAllWithJdbcTemplate() {
        var singers = singerRepo.findAll().stream().toList();
        assertEquals(3, singers.size());
        singers.forEach(singer -> LOGGER.info(singer.toString()));
    }
}
