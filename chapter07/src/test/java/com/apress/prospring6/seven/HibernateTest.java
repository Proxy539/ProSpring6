package com.apress.prospring6.seven;

import com.apress.prospring6.seven.base.config.HibernateConfig;
import com.apress.prospring6.seven.base.dao.SingerDao;
import com.apress.prospring6.seven.base.entities.Album;
import com.apress.prospring6.seven.base.entities.Singer;
import jakarta.annotation.PostConstruct;
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
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Testcontainers
@Sql({"classpath:testcontainers/drop-schema.sql", "classpath:testcontainers/create-schema.sql"})
@SpringJUnitConfig(classes = {HibernateConfig.class, HibernateTest.TestContainersConfig.class})
public class HibernateTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(HibernateTest.class);

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
    SingerDao singerDao;

    @Test
    @DisplayName("should return all singers")
    void testFindAll() {
        var singers = singerDao.findAll();
        assertEquals(3, singers.size());
        singers.forEach(singer -> LOGGER.info(singer.toString()));
    }

    @Test
    @DisplayName("should return singer by id")
    void testFindById() {
        var singer = singerDao.findById(2L);
        assertEquals("Ben", singer.getFirstName());
        LOGGER.info(singer.toString());
    }

    @Test
    @DisplayName("should insert a singer with associations")
    @Sql(statements = {
            "delete from ALBUM where SINGER_ID = (select ID from SINGER where FIRST_NAME = 'BB')",
            "delete from SINGER_INSTRUMENT where SINGER_ID = (select ID from SINGER where FIRST_NAME = 'BB')",
            "delete from SINGER where FIRST_NAME = 'BB'"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testInsertSinger() {
        var singer = new Singer();
        singer.setFirstName("BB");
        singer.setLastName("King");
        singer.setBirthDate(LocalDate.of(1940, 8, 16));

        var album = new Album();
        album.setTitle("My Kind of Blues");
        album.setReleaseDate(LocalDate.of(1961, 7, 18));
        singer.addAlbum(album);

        album = new Album();
        album.setTitle("A Heart Full of Blues");
        album.setReleaseDate(LocalDate.of(1962, 3, 20));
        singer.addAlbum(album);
        singerDao.save(singer);

        assertNotNull(singer.getId());

        var singers = singerDao.findAllWithAlbum();
        assertEquals(4, singers.size());
        listSingersWithAssociations(singers);
    }

    @Test
    @SqlMergeMode(SqlMergeMode.MergeMode.OVERRIDE)
    @SqlGroup({
            @Sql(scripts = {"classpath:testcontainers/drop-schema.sql", "classpath:testcontainers/create-schema.sql",
                    "classpath:testcontainers/add-nina.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = {"classpath:testcontainers/remove-nina.sql"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    void testUpdate() {
        Singer singer = singerDao.findById(5L);
        //making sure such singer exists

        assertNotNull(singer);
        //making sure we got expected singer
        assertEquals("Simone", singer.getLastName());
        //retrieve the album
        Album album = singer.getAlbums().stream()
                .filter(a -> a.getTitle().equals("I Put a Spell on You."))
                .findFirst()
                .orElse(null);

        assertNotNull(album);

        singer.setFirstName("Eunice Katheen");
        singer.setLastName("Waymon");
        singer.removeAlbum(album);
        int version = singer.getVersion();

        var nina = singerDao.save(singer);
        assertEquals(version + 1, nina.getVersion());

        //test the update
        listSingersWithAssociations(singerDao.findAllWithAlbum());
    }

    @Test
    @SqlMergeMode(SqlMergeMode.MergeMode.OVERRIDE)
    @Sql(scripts = {"classpath:testcontainers/drop-schema.sql", "classpath:testcontainers/create-schema.sql",
            "classpath:testcontainers/add-chuck.sql"},
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void deleteTest() {
        Singer singer = singerDao.findById(6L);

        //making sure such singer exists
        assertNotNull(singer);

        singerDao.delete(singer);

        listSingersWithAssociations(singerDao.findAllWithAlbum());
    }


    @Configuration
    @Import(HibernateConfig.class)
    public static class TestContainersConfig {
        @Autowired
        Properties hibernateProperties;

        @PostConstruct
        public void initialize() {
            hibernateProperties.put(Environment.FORMAT_SQL, true);
            hibernateProperties.put(Environment.USE_SQL_COMMENTS, true);
            hibernateProperties.put(Environment.SHOW_SQL, true);
        }
    }

    private static void listSingersWithAssociations(List<Singer> singers) {
        LOGGER.info("---- Listing singers with instruments:");
    }
}
