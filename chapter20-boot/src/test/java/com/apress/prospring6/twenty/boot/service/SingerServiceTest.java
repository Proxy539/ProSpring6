package com.apress.prospring6.twenty.boot.service;

import com.apress.prospring6.twenty.boot.model.Singer;
import com.apress.prospring6.twenty.boot.problem.SaveException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.r2dbc.test.autoconfigure.DataR2dbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.MountableFile;
import reactor.test.StepVerifier;

import java.time.LocalDate;

@Testcontainers
@DataR2dbcTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Import(SingerServiceImpl.class)
public class SingerServiceTest {

    @Container
    static MariaDBContainer<?> mariaDB = new MariaDBContainer<>("mariadb:latest")
            .withCopyFileToContainer(MountableFile.forClasspathResource("testcontainers/create-schema.sql"),
                    "/docker-entrypoint-initdb.d/init.sql");

    @Autowired
    SingerService singerService;

    @Order(2)
    @Test
    void testFindAll() {
        singerService.findAll()
                .log()
                .as(StepVerifier::create)
                .expectNextCount(4)
                .verifyComplete();
    }

    @Order(3)
    @Test
    void testFindById() {
        singerService.findById(1L)
                .log()
                .as(StepVerifier::create)
                .expectNextMatches(s -> "John".equals(s.getFirstName()) && "Mayer".equals(s.getLastName()))
                .verifyComplete();
    }

    @Order(8)
    @Test // duplicate firstname and lastname
    public void testNoCreateSinger() {
        singerService.save(Singer.builder()
                .firstName("John")
                .lastName("Mayer")
                .birthDate(LocalDate.now())
                .build())
                .log()
                .as(StepVerifier::create)
                .verifyError(SaveException.class);
    }

    @Order(9)
    @Test
    public void testUpdateSinger() {
        singerService.update(4L, Singer.builder()
                .firstName("Erik Patrick")
                .lastName("Clapton")
                .birthDate(LocalDate.now())
                .build())
                .log()
                .as(StepVerifier::create)
                .expectNextMatches(s -> "Erik Patrick".equals(s.getFirstName()) && "Clapton".equals(s.getLastName()))
                .verifyComplete();
    }

    @Order(10)
    @Test
    public void testUpdateSingerWithDuplicateData() {
        singerService.update(4L, Singer.builder()
                .firstName("John")
                .lastName("Mayer")
                .birthDate(LocalDate.now())
                .build())
                .log()
                .as(StepVerifier::create)
                .verifyError(SaveException.class);
    }

    @Order(11)
    @Test
    public void testFailedCreateSinger() {
        singerService.update(4L, Singer.builder()
                .firstName("Test")
                .birthDate(LocalDate.now())
                .build())
                .log()
                .as(StepVerifier::create)
                .verifyError(SaveException.class);
    }

    @Order(12)
    @Test
    public void testDeleteSinger() {
        singerService.delete(4L)
                .log()
                .as(StepVerifier::create)
                .expectNextCount(0)
                .verifyComplete();
    }

    @DynamicPropertySource
    static void registerDynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.r2dbc.url", () -> "r2dbc:mariadb://" + mariaDB.getHost() + ":" +
                mariaDB.getFirstMappedPort() + "/" + mariaDB.getDatabaseName());
        registry.add("spring.r2dbc.username", () -> mariaDB.getUsername());
        registry.add("spring.r2dbc.password", () -> mariaDB.getPassword());
    }
}
