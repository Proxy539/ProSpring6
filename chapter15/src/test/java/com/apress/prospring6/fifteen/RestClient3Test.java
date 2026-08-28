package com.apress.prospring6.fifteen;

import com.apress.prospring6.fifteen.boot.Chapter15Application;
import com.apress.prospring6.fifteen.entities.Singer;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

// the duplicate-name check needs a singer already in the table before the negative call - a
// fresh context/H2 schema/seed data avoids depending on run order relative to the other tests.
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
@SpringBootTest(classes = Chapter15Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestClient3Test {
    final Logger LOGGER = LoggerFactory.getLogger(RestClient3Test.class);

    @Value("${local.server.port}")
    private int port;

    RestTemplate restTemplate = new RestTemplate();

    private String uriCreateSinger() {
        return "http://localhost:" + port + "/singer3/";
    }

    @Test
    public void testNegativeCreate() throws URISyntaxException {
        LOGGER.info("--> Testing create singer with a first/last name that already exists");
        Singer singerNew = new Singer();
        singerNew.setFirstName("Ben");
        singerNew.setLastName("Barners");
        singerNew.setBirthDate(LocalDate.now());

        restTemplate.postForObject(uriCreateSinger(), singerNew, Singer.class);

        RequestEntity<Singer> req = new RequestEntity<>(singerNew, HttpMethod.POST, new URI(uriCreateSinger()));

        assertThrowsExactly(HttpClientErrorException.BadRequest.class, () -> restTemplate.exchange(req, HttpStatus.class));
    }
}
