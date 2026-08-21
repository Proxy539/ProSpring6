package com.apress.prospring6.nine;

import com.apress.prospring6.nine.config.TransactionCfg;
import com.apress.prospring6.nine.entities.Album;
import com.apress.prospring6.nine.ex.TitleTooLongException;
import com.apress.prospring6.nine.services.AllService;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.PersistenceException;
import org.hibernate.cfg.Environment;
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
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Testcontainers
@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
@Sql({"classpath:testcontainers/drop-schema.sql", "classpath:testcontainers/create-schema.sql"})
@SpringJUnitConfig(classes = {AllServiceTest.TestContainersConfig.class})
public class AllServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(AllServiceTest.class);

    @Container
    static MariaDBContainer<?> mariaDB = new MariaDBContainer<>("mariadb:10.7.4-focal");

    @DynamicPropertySource
    static void setup(DynamicPropertyRegistry registry) {
        registry.add("jdbc.driverClassName", mariaDB::getDriverClassName);
        registry.add("jdbc.url", mariaDB::getJdbcUrl);
        registry.add("jdbc.username", mariaDB::getUsername);
        registry.add("jdbc.password", mariaDB::getPassword);
    }

    @Autowired
    AllService service;

    @Test
    @DisplayName("should return all singers and albums")
    void testFindAll() {
        var singers = service.findAllWithAlbums()
                .peek(s -> {
                    LOGGER.info(s.toString());
                    if (s.getAlbums() != null) {
                        s.getAlbums().forEach(a -> LOGGER.info("\tAlbum: " + a.toString()));
                    }
                })
                .toList();
        assertEquals(3, singers.size());
    }

    @Test
    @SqlGroup({
            @Sql(scripts = {"classpath:testcontainers/add-nina.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    @DisplayName("should update a singer")
    void testUpdate() {
        var singer = service.findByIdWithAlbums(5L).orElse(null);
        //making sure such singer exists
        assertNotNull(singer);

        //retrieve the album
        var album = singer.getAlbums().stream()
                .filter(a -> a.getTitle().equals("I Put a Spell on You"))
                .findFirst()
                .orElse(null);

        assertNotNull(album);

        singer.setFirstName("Eunice Kathleen");
        singer.setLastName("Waymon");
        singer.removeAlbum(album);
        int version = singer.getVersion();

        service.update(singer);

        var nina = service.findByIdWithAlbums(5L).orElse(null);

        assertAll("nina was updated",
                () -> assertNotNull(nina),
                () -> assertEquals(version + 1, nina.getVersion()),
                () -> assertEquals(2, nina.getAlbums().size()));
    }

    @Test
    void testCount() {
        var singers = service.findAllWithAlbums().collect(Collectors.toSet());
        var count = service.countSingers();

        assertEquals(count, singers.size());
    }

    @Test
    @SqlGroup({
            @Sql(scripts = {"classpath:testcontainers/add-nina.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    @DisplayName("should perform a rollback because PersistenceException")
    void testRollbackRuntimeUpdate() {
        // (1)
        var singer = service.findByIdWithAlbums(5L).orElse(null);
        assertNotNull(singer);

        // (2)
        singer.setFirstName("Eunice Kathleen");
        singer.setLastName("Waymon");

        var album = new Album();
        album.setTitle("Little Girl Blue");
        album.setReleaseDate(LocalDate.of(1959, 2, 20));
        album.setSinger(singer);

        // (3)
        var albums = Set.of(album);

        // (4)
        assertThrows(PersistenceException.class,
                () -> service.saveSingerWithAlbums(singer, albums),
                "PersistenceException not thrown!");

        // (5)
        var nina = service.findByIdWithAlbums(5L).orElse(null);
        assertAll("nina was not updated", () -> assertNotNull(nina),
                () -> assertNotEquals("Eunice Kathleen", nina.getFirstName()),
                () -> assertNotEquals("Waymon", nina.getLastName()));
    }

    @Test
    @SqlGroup({
            @Sql(scripts = {"classpath:testcontainers/add-nina.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    @DisplayName("should perform a rollback because TitleTooLongException")
    void testRollbackCheckedUpdate() {
        var singer = service.findByIdWithAlbums(5L).orElse(null);
        assertNotNull(singer);

        singer.setFirstName("Eunice Kathleen");
        singer.setLastName("Waymon");

        var album = new Album();
        album.setTitle("""
                Sit there and count four fingers
                What can you do? 
                Old girl you're through 
                Sit there, count your little fingers
                Unhappy little girl blue
                """);
        album.setReleaseDate(LocalDate.of(1959, 2, 20));
        album.setSinger(singer);

        var albums = Set.of(album);

        assertThrows(TitleTooLongException.class,
                () -> service.saveSingerWithAlbums(singer, albums),
                "TitleTooLongException not thrown!");

        var nina = service.findByIdWithAlbums(5L).orElse(null);
        assertAll("nina was not updated",
                () -> assertNotNull(nina),
                () -> assertNotEquals("Eunice Kathleen", nina.getFirstName()),
                () -> assertNotEquals("Waymon", nina.getLastName()));
    }

    @Configuration
    @Import(TransactionCfg.class)
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
