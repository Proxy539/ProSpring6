package com.apress.prospring6.fifteen;

import com.apress.prospring6.fifteen.boot.Chapter15Application;
import com.apress.prospring6.fifteen.entities.Singer;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

// Each test mutates the SINGER table via real HTTP calls (a different thread than the test
// method, so the usual @Transactional test rollback doesn't apply) - a fresh context/H2
// schema/seed data per test method keeps every test's starting data set identical.
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
@SpringBootTest(classes = Chapter15Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestClientTest {

    final Logger LOGGER = LoggerFactory.getLogger(RestClientTest.class);

    @Value("${local.server.port}")
    private int port;

    RestTemplate restTemplate = new RestTemplate();

    private String uriSingerRoot() {
        return "http://localhost:" + port + "/singer/";
    }

    private String uriSingerWithId() {
        return "http://localhost:" + port + "/singer/{id}";
    }

    @Test
    public void testFindAll() {
        LOGGER.info("--> Testing retrieve all singers");
        var singers = restTemplate.getForObject(uriSingerRoot(), Singer[].class);
        assertEquals(15, singers.length);
        Arrays.stream(singers).forEach(s -> LOGGER.info(s.toString()));
    }

    @Test
    public void testFindById() {
        LOGGER.info("--> Testing retrieve a singer by id: 1");
        var singer = restTemplate.getForObject(uriSingerWithId(), Singer.class, 1);
        assertNotNull(singer);
        LOGGER.info(singer.toString());
    }

    @Test
    public void testCreate() {
        LOGGER.info("--> Testing create singer");
        Singer singerNew = new Singer();
        singerNew.setFirstName("TEST");
        singerNew.setLastName("Singer");
        singerNew.setBirthDate(LocalDate.now());
        singerNew = restTemplate.postForObject(uriSingerRoot(), singerNew, Singer.class);

        LOGGER.info("Singer created successfully: " + singerNew);
    }

    @Test
    public void testDelete() {
        LOGGER.info("--> Testing delete a newly created singer");
        Singer singerNew = new Singer();
        singerNew.setFirstName("TO_DELETE");
        singerNew.setLastName("Singer");
        singerNew.setBirthDate(LocalDate.now());
        singerNew = restTemplate.postForObject(uriSingerRoot(), singerNew, Singer.class);

        var initialCount = restTemplate.getForObject(uriSingerRoot(), Singer[].class).length;
        restTemplate.delete(uriSingerWithId(), singerNew.getId());
        var afterDeleteCount = restTemplate.getForObject(uriSingerRoot(), Singer[].class).length;
        assertEquals((initialCount - afterDeleteCount), 1);
    }

    @Test
    public void testUpdate() {
        LOGGER.info("--> Testing update singer by id: 1");
        var singer = restTemplate.getForObject(uriSingerWithId(), Singer.class, 1);
        singer.setFirstName("John Marvelous");
        restTemplate.put(uriSingerWithId(), singer, 1);
        LOGGER.info("Singer update successfully: " + singer);
    }

    @Test
    public void testFindAllWithExecute() {
        LOGGER.info("--> Testing retrieve all singers");
        restTemplate.execute(uriSingerRoot(), HttpMethod.GET, request -> LOGGER.debug("Request submitted ..."),
                response -> {
                    assertEquals(HttpStatus.OK, response.getStatusCode());
                    return new String(response.getBody().readAllBytes());
                });
    }

    @Test
    public void testCreateWithExchange() {
        LOGGER.info("--> Testing create singer");
        Singer singerNew = new Singer();
        singerNew.setFirstName("TEST");
        singerNew.setLastName("Singer");
        singerNew.setBirthDate(LocalDate.now());

        HttpEntity<Singer> request = new HttpEntity<>(singerNew);
        ResponseEntity<Singer> created = restTemplate.exchange(uriSingerRoot(), HttpMethod.POST, request, Singer.class);

        assertEquals(HttpStatus.CREATED, created.getStatusCode());

        var singerCreated = created.getBody();
        assertNotNull(singerCreated);

        LOGGER.info("Singer created successfully: " + singerCreated);
    }
}
