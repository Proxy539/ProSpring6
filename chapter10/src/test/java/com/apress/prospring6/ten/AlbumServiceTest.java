package com.apress.prospring6.ten;

import com.apress.prospring6.ten.service.AlbumService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@Sql({"classpath:testcontainers/drop-schema.sql", "classpath:testctontainers/create-schema.sql"})
@SpringJUnitConfig(classes = {AlbumServiceTest.TestContainerConfig.class})
public class AlbumServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlbumServiceTest.class);

    @Autowired
    AlbumService albumService;

    @Test
    public void testFindWithReleaseDateGreaterThan() {
        var albums = albumService
                .findWithReleaseDateGreaterThan(LocalDate.of(2010, 1, 1))
                .peek(s -> LOGGER.info(s.toString()))
                .toList();

        assertEquals(2, albums.size());
    }

    @Test
    public void testFindByTitle() {
        var albums = albumService
                .findByTitle("The")
                .peek(s -> LOGGER.info(s.toString()))
                .toList();

        assertEquals(1, albums.size());
    }
}
