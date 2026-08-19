package com.apress.prospring6.seven;

import com.apress.prospring6.seven.base.config.HibernateConfig;
import com.apress.prospring6.seven.base.dao.SingerDao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@Sql({"classpath:testcontainers/drop-schema.sql", "classpath:testcontainers/create-schema.sql"})
@SpringJUnitConfig(classes = {HibernateConfig.class})
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
}
