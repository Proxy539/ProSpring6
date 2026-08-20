package com.apress.prospring6.eight;

import com.apress.prospring6.eight.config.JpaConfig;
import com.apress.prospring6.eight.entities.Album;
import com.apress.prospring6.eight.entities.Singer;
import com.apress.prospring6.eight.service.SingerService;
import com.apress.prospring6.eight.view.SingerSummaryService;
import jakarta.annotation.PostConstruct;
import org.apache.commons.logging.Log;
import org.hibernate.cfg.Environment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.jdbc.SqlMergeMode;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
@Sql({"classpath:testcontainers/drop-schema.sql", "classpath:testcontainers/create-schema.sql"})
@SpringJUnitConfig(classes = {SingerServiceTest.TestContainersConfig.class})
public class SingerServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SingerServiceTest.class);

    @Autowired
    SingerSummaryService singerSummaryService;

    @Container
    static MariaDBContainer<?> mariaDB = new MariaDBContainer<>("mariadb:10.7.4-focal");

    // BasicDataSourceCfg builds its HikariDataSource from jdbc.* properties loaded via
    // @PropertySource("classpath:db/jdbc.properties"); override them here so the Spring
    // context connects to the ephemeral container instead of that static config.
    @DynamicPropertySource
    static void registerDataSourceProperties(DynamicPropertyRegistry registry) {
        registry.add("jdbc.url", mariaDB::getJdbcUrl);
        registry.add("jdbc.username", mariaDB::getUsername);
        registry.add("jdbc.password", mariaDB::getPassword);
        registry.add("jdbc.driverClassName", mariaDB::getDriverClassName);
    }

    @Autowired
    SingerService singerService;

    @Test
    @DisplayName("Should return all singers")
    void testFindAll() {
        var singers = singerService.findAll().toList();
        assertEquals(3, singers.size());
        singers.forEach(singer -> LOGGER.info(singer.toString()));
    }

    @Test
    @DisplayName("should return all singers")
    void testFindAllWithAlbum() {
        var singers = singerService.findAllWithAlbum().toList();
        assertEquals(3, singers.size());
        singers.forEach(s -> {
            LOGGER.info(s.toString());
            if (s.getAlbums() != null) {
                s.getAlbums().forEach(a -> LOGGER.info("\tAlbum: " + a.toString()));
            }
            if (s.getInstruments() != null) {
                s.getInstruments().forEach(i -> LOGGER.info("\tInstrument: " + i.getInstrumentId()));
            }
        });
    }

    @Test
    @DisplayName("should return all singers and their most recent album as record")
    void testFindAllWithAlbumAsRecords() {
        var singers = singerSummaryService.findAllAsRecords()
                .peek(s -> LOGGER.info(s.toString()))
                .toList();

        assertEquals(2, singers.size());
    }

    @Test
    @DisplayName("should insert a singer with associations")
    @Sql(statements = {
            "delete from ALBUM where SINGER_ID = (select ID from SINGER where FIRST_NAME ='BB')",
            "delete from SINGER_INSTRUMENT where SINGER_ID = (select ID from SINGER where FIRST_NAME = 'BB')",
            "delete from SINGER where FIRST_NAME = 'BB'"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testInsert() {
        var singer = new Singer();
        singer.setFirstName("BB");
        singer.setLastName("King");
        singer.setBirthDate(LocalDate.of(1940, 8, 16));

        var album = new Album();
        album.setTitle("A Heart Full of Blues");
        album.setReleaseDate(LocalDate.of(1962, 3, 20));
        album.setSinger(singer);
        singer.getAlbums().add(album);
        singerService.save(singer);

        Assertions.assertNotNull(singer.getId());

        var singers = singerService.findAllWithAlbum().peek(
                s -> {
                    LOGGER.info(s.toString());
                    if (s.getAlbums() != null) {
                        s.getAlbums().forEach(a -> LOGGER.info("\tAlbum: " + a.toString()));
                    }
                    if (s.getInstruments() != null) {
                        s.getInstruments().forEach(i -> LOGGER.info("\tInstrument: " + i.getInstrumentId()));
                    }
                }
        ).toList();

        assertEquals(4, singers.size());
    }

    @Test
    @SqlGroup({
            @Sql(scripts = {"classpath:testcontainers/add-nina.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = {"classpath:testcontainers/remove-nina.sql"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    @DisplayName("should update a singer")
    void testUpdate() {
        var singer = singerService.findById(5L).orElse(null);
        //making sure such singer exists
        assertNotNull(singer);
        //making sure we got expected singer
        assertEquals("Simone", singer.getLastName());
        //retrieve the album
        var album = singer.getAlbums().stream()
                .filter(a -> a.getTitle().equals("I Put a Spell on You"))
                .findFirst()
                .orElse(null);
        Assertions.assertNotNull(album);

        singer.setFirstName("Eunice Kathleen");
        singer.setLastName("Waymon");
        singer.getAlbums().remove(album);
        int version = singer.getVersion();

        singerService.save(singer);

        var nina = singerService.findById(5L).orElse(null);

        assertAll("nina was updated",
                () -> Assertions.assertNotNull(nina),
                () -> Assertions.assertEquals(version + 1, nina.getVersion()));
    }

    @Test
    @DisplayName("should update album set")
    public void testUpdateAlbumSet() {
        var singer = singerService.findById(1L).orElse(null);
        //making sure such singer exists
        assertNotNull(singer);
        //making sure we got expected record
        assertEquals("Mayer", singer.getLastName());
        //retrieve the album
        var album = singer.getAlbums()
                .stream()
                .filter(a -> a.getTitle().equals("Battle Studies"))
                .findAny()
                .orElse(null);

        singer.setFirstName("John Clayton");
        singer.getAlbums().remove(album);
        singerService.save(singer);

        var singers = singerService.findAllWithAlbum()
                .peek(s -> {
                    LOGGER.info(s.toString());
                    if (s.getAlbums() != null) {
                        s.getAlbums().forEach(a -> LOGGER.info("\tAlbum: " + a.toString()));
                    }
                    if (s.getInstruments() != null) {
                        s.getInstruments().forEach(i -> LOGGER.info("\tInstrument: " + i.getInstrumentId()));
                    }
                }).toList();

        assertEquals(3, singers.size());
    }

    @Test
    @Sql(scripts = {"classpath:testcontainers/add-chuck.sql"},
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @DisplayName("should delete a singer")
    public void testDelete() {
        var singer = singerService.findById(6L).orElse(null);
        //making sure such singer exists
        assertNotNull(singer);
        singerService.delete(singer);

        var deleted = singerService.findById(6L);
        assertTrue(deleted.isEmpty());
    }


    @Configuration
    @Import(JpaConfig.class)
    public static class TestContainersConfig {
        @Autowired
        Properties jpaProperties;

        @PostConstruct
        public void initialize() {
            jpaProperties.put(Environment.FORMAT_SQL, true);
            jpaProperties.put(Environment.USE_SQL_COMMENTS, true);
            jpaProperties.put(Environment.SHOW_SQL, true);
        }
    }
}
