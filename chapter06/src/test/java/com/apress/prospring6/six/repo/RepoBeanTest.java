package com.apress.prospring6.six.repo;

import com.apress.prospring6.six.config.SpringDatasourceCfg;
import com.apress.prospring6.six.records.Album;
import com.apress.prospring6.six.records.Singer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RepoBeanTest {

    private static Logger LOGGER = LoggerFactory.getLogger(RepoBeanTest.class);

    private static final String DB_URL = "jdbc:mariadb://localhost:3306/prospring?useSSL=false";
    private static final String DB_USER = "prospring";
    private static final String DB_PASSWORD = "prospring";

    // Each test runs against the same real MariaDB instance and inserts/updates rows with no
    // transaction rollback, so re-seed a known baseline before every test rather than relying
    // on execution order or accumulated state from earlier runs.
    @BeforeEach
    public void resetDatabase() throws Exception {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement()) {
            statement.execute("delete from ALBUM");
            statement.execute("delete from SINGER");
            statement.execute("alter table SINGER auto_increment = 1");
            statement.execute("alter table ALBUM auto_increment = 1");

            statement.execute("insert into SINGER (id, first_name, last_name, birth_date) values " +
                    "(1, 'John', 'Mayer', '1977-10-16')");
            statement.execute("insert into SINGER (id, first_name, last_name, birth_date) values " +
                    "(2, 'Ben', 'Barnes', '1981-08-20')");
            statement.execute("insert into SINGER (id, first_name, last_name, birth_date) values " +
                    "(3, 'John', 'Butler', '1975-04-01')");

            statement.execute("insert into ALBUM (id, singer_id, title, release_date) values " +
                    "(1, 1, 'The Search For Everything', '2017-01-20')");
            statement.execute("insert into ALBUM (id, singer_id, title, release_date) values " +
                    "(2, 1, 'Battle Studies', '2009-11-17')");
            statement.execute("insert into ALBUM (id, singer_id, title, release_date) values " +
                    "(3, 2, ' 11:11 ', '2021-09-18')");
        }
    }

    @Test
    public void testFindAllWithMappingSqlQuery() {
        var ctx = new AnnotationConfigApplicationContext(SpringDatasourceCfg.class);
        var singerRepo = ctx.getBean("singerRepo", SingerRepo.class);
        assertNotNull(singerRepo);

        final var singers = singerRepo.findAll();
        assertEquals(3, singers.size());
        singers.forEach(singer -> LOGGER.info(singer.toString()));

        ctx.close();
    }

    @Test
    public void testFindByNameWithMappingSqlQuery() {
        var ctx = new AnnotationConfigApplicationContext(SpringDatasourceCfg.class);
        var singerRepo = ctx.getBean("singerRepo", SingerRepo.class);
        assertNotNull(singerRepo);

        var singers = singerRepo.findByFirstName("Ben");
        assertEquals(1, singers.size());
        LOGGER.info("Result: {}", singers.get(0));

        ctx.close();
    }

    @Test
    public void testUpdateWithSqlUpdate() {
        var ctx = new AnnotationConfigApplicationContext(SpringDatasourceCfg.class);
        var singerRepo = ctx.getBean("singerRepo", SingerRepo.class);
        assertNotNull(singerRepo);

        Singer singer = new Singer(1L, "John Clayton", "Mayer", LocalDate.of(1977, 9, 16), List.of());
        singerRepo.update(singer);

        var singers = singerRepo.findByFirstName("John Clayton");
        assertEquals(1, singers.size());
        LOGGER.info("Result: {}", singers.get(0));

        ctx.close();
    }

    @Test
    public void testInsertWithSqlUpdate() {
        var ctx = new AnnotationConfigApplicationContext(SpringDatasourceCfg.class);
        var singerRepo = ctx.getBean("singerRepo", SingerRepo.class);
        assertNotNull(singerRepo);

        Singer singer = new Singer(null, "Ed", "Sheeran", LocalDate.of(1991, 1, 17), List.of());
        singerRepo.insert(singer);

        var singers = singerRepo.findByFirstName("Ed");
        assertEquals(1, singers.size());
        LOGGER.info("Result: {}", singers.get(0));

        ctx.close();
    }

    @Test
    public void testInsertAlbumsWithBatchSqlUpdate() {
        var ctx = new AnnotationConfigApplicationContext(SpringDatasourceCfg.class);
        var singerRepo = ctx.getBean("singerRepo", SingerRepo.class);
        assertNotNull(singerRepo);

        var singer = new Singer(null, "BB", "King", LocalDate.of(1940, 8 ,16), new ArrayList<>());
        var album = new Album(null, null, "My Kind of Blues", LocalDate.of(1961, 7, 18));

        singer.albums().add(album);

        album = new Album(null, null, "A Heart Full of Blues", LocalDate.of(1962, 3, 20));
        singer.albums().add(album);

        singerRepo.insertWithAlbum(singer);

        var singers = singerRepo.findAllWithAlbums();
        assertEquals(4, singers.size());
        singers.forEach(s -> LOGGER.info(s.toString()));

        ctx.close();
    }
}
