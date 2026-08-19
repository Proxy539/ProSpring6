package com.apress.prospring6.six.repo;

import com.apress.prospring6.six.config.BasicDataSourceCfg;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@SpringJUnitConfig(classes = {BasicDataSourceCfg.class, SingerJdbcRepo.class})
public class StoredFunctionV1Test {

    @Container
    static MariaDBContainer<?> mariaDB = new MariaDBContainer<>("mariadb:10.7.4-focal")
            .withInitScript("testcontainers/init-schema-and-function.sql");

    @DynamicPropertySource
    static void setUp(DynamicPropertyRegistry registry) {
        registry.add("db.driverClassName", mariaDB::getDriverClassName);
        registry.add("db.url", mariaDB::getJdbcUrl);
        registry.add("db.username", mariaDB::getUsername);
        registry.add("db.password", mariaDB::getPassword);
    }

    @Autowired
    SingerRepo singerRepo;

    @Test
    void testFindAllQuery() {
        var singers = singerRepo.findAll();
        assertEquals(3, singers.size());
    }

    @Test
    void testStoredFunction() {
        var firstName = singerRepo.findFirstNameById(2L).orElse(null);
        assertEquals("Ben", firstName);
    }
}
