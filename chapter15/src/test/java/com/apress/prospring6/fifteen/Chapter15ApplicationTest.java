package com.apress.prospring6.fifteen;

import com.apress.prospring6.fifteen.boot.Chapter15Application;
import com.apress.prospring6.fifteen.entities.Singer;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

// testNegativeCreate mutates the SINGER table via a real HTTP call (a different thread than the
// test method, so the usual @Transactional test rollback doesn't apply) - a fresh context/H2
// schema/seed data per test method keeps every test's starting data set identical.
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
@AutoConfigureTestRestTemplate
@SpringBootTest(classes = Chapter15Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class Chapter15ApplicationTest {

    final Logger LOGGER = LoggerFactory.getLogger(Chapter15ApplicationTest.class);

    @Value("${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testFindAll() {
        LOGGER.info("--> Testing retrieved all singers");
        var singers = restTemplate.getForObject("http://localhost:" + port + "/singer/",
                Singer[].class);
        assertTrue(singers.length >= 15);
        Arrays.stream(singers)
                .forEach(s -> LOGGER.info(s.toString()));
    }

    @Test
    public void testPositiveFindById() throws URISyntaxException {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        RequestEntity<HttpHeaders> req = new RequestEntity<>(headers, HttpMethod.GET,
                new URI("http://localhost:" + port + "/singer/1"));
        LOGGER.info("--> Testing retrieve a singer by id : 1");
        ResponseEntity<Singer> response = restTemplate.exchange(req, Singer.class);
        assertAll("testPositiveFindById",
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertTrue(Objects.requireNonNull(response.getHeaders())
                        .get(HttpHeaders.CONTENT_TYPE).contains(MediaType.APPLICATION_JSON_VALUE)),
                () -> assertNotNull(response.getBody()),
                () -> assertEquals(Singer.class, response.getBody().getClass()));
    }

    @Test
    public void testNegativeFindById() throws URISyntaxException {
        LOGGER.info("--> Testing retrieve a singer by id : 99");
        RequestEntity<Singer> req = new RequestEntity<>(HttpMethod.GET, new URI("http://localhost:" + port + "/singer/99"));

        ResponseEntity<Singer> response = restTemplate.exchange(req, Singer.class);
        assertAll("testNegativeFindById",
                () -> assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()),
                () -> assertNull(response.getBody().getFirstName()),
                () -> assertNull(response.getBody().getLastName()));
    }

    @Test
    public void testNegativeCreate() throws URISyntaxException {
        LOGGER.info("--> Testing create singer");

        Singer singerNew = new Singer();
        singerNew.setFirstName("Ben");
        singerNew.setLastName("Barnes");
        singerNew.setBirthDate(LocalDate.now());

        RequestEntity<Singer> req = new RequestEntity<>(singerNew, HttpMethod.POST, new URI("http://localhost:" + port + "/singer/"));

        ResponseEntity<String> response = restTemplate.exchange(req, String.class);
        assertAll("testNegativeCreate",
                () -> assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode()),
                // exact Hibernate exception wording is version-specific - this Spring Boot 4.1 /
                // Hibernate combination reports "could not execute statement [Unique index or
                // primary key violation: ... FIRST_NAME ...]" rather than the older
                // "SQL [n/a]; constraint [...]" template, so assert on the parts that identify
                // the actual failure (a unique-index hit naming the FIRST_NAME constraint).
                () -> assertTrue(response.getBody().contains("Unique index or primary key violation")),
                () -> assertTrue(response.getBody().contains("FIRST_NAME")));
    }
}
