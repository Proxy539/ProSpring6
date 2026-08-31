package com.apress.prospring6.twenty.boot;

import com.apress.prospring6.twenty.boot.model.Singer;
import com.apress.prospring6.twenty.boot.repo.SingerRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.r2dbc.test.autoconfigure.DataR2dbcTest;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.MountableFile;
import reactor.test.StepVerifier;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Testcontainers
@DataR2dbcTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RepositoryTest {

    @Container
    static MariaDBContainer<?> mariaDB = new MariaDBContainer<>("mariadb:latest")
            .withCopyFileToContainer(MountableFile.forClasspathResource("testcontainers/create-schema.sql"),
                    "/docker-entrypoint-initdb.d/init.sql");

    @Autowired
    SingerRepo singerRepo;

    @Autowired
    R2dbcEntityTemplate template;

    @Order(1)
    @BeforeEach
    public void testRepoExists() {
        assertNotNull(singerRepo);
    }

    @Order(2)
    @Test
    public void testCount() {
        singerRepo.count()
                .log()
                .as(StepVerifier::create)
                .expectNextMatches(p -> p == 4)
                .verifyComplete();
    }

    @Order(3)
    @Test
    public void testFindByFirstName() {
        singerRepo.findByFirstName("John")
                .log()
                .as(StepVerifier::create)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Order(4)
    @Test
    public void testFindByFirstNameAndLastName() {
        singerRepo.findByFirstNameAndLastName("John", "Mayer")
                .log()
                .as(StepVerifier::create)
                .expectNext(Singer.builder()
                        .id(1L)
                        .firstName("John")
                        .lastName("Mayer")
                        .birthDate(LocalDate.of(1977, 10, 16))
                        .build())
                .verifyComplete();
    }

    @Order(5)
    @Test
    public void testCreateSinger() {
        singerRepo.save(Singer.builder()
                .firstName("Test")
                .lastName("Test")
                .birthDate(LocalDate.now())
                .build())
                .log()
                .as(StepVerifier::create)
                .assertNext(s -> assertNotNull(s.getId()))
                .verifyComplete();
    }

    @Order(6)
    @Test
    public void testDeleteSinger() {
        singerRepo.deleteById(4L)
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
