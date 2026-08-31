package com.apress.prospring6.twenty.boot.webclient;

import com.apress.prospring6.twenty.boot.Chapter20Application;
import com.apress.prospring6.twenty.boot.model.Singer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.MountableFile;

import java.time.LocalDate;

@Testcontainers
@SpringBootTest(classes = Chapter20Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReactiveSingerControllerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReactiveSingerControllerTest.class);

    @Container
    static MariaDBContainer<?> mariaDB = new MariaDBContainer<>("mariadb:latest")
            .withCopyFileToContainer(MountableFile.forClasspathResource("testcontainers/create-schema.sql"),
                    "/docker-entrypoint-initdb.d/init.sql");

    @Value("${local.server.port}")
    private int port;

    private WebTestClient controllerClient;

    @BeforeEach
    void setUp() {
        controllerClient = WebTestClient
                .bindToServer()
                .baseUrl("http://localhost:" + port + "/reactive/singer")
                .build();
    }

    @Test
    void shouldReturnAFew() {
        controllerClient.get()
                .uri(uriBuilder -> uriBuilder.queryParam("name", "John").build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.length()").isEqualTo(2);
    }

    @Test
    void shouldFailToCreateJohnMayer() {
        controllerClient.post()
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(Singer.builder()
                        .firstName("John")
                        .lastName("Mayer")
                        .birthDate(LocalDate.of(1977, 10, 16))
                        .build())
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .consumeWith(body -> LOGGER.debug("body: {}", body));
    }

    @DynamicPropertySource
    static void registerDynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.r2dbc.url", () -> "r2dbc:mariadb://" + mariaDB.getHost() + ":" +
                mariaDB.getFirstMappedPort() + "/" + mariaDB.getDatabaseName());
        registry.add("spring.r2dbc.username", () -> mariaDB.getUsername());
        registry.add("spring.r2dbc.password", () -> mariaDB.getPassword());
    }
}
