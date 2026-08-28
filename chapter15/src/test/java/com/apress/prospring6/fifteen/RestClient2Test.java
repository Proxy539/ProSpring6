package com.apress.prospring6.fifteen;

import com.apress.prospring6.fifteen.boot.Chapter15Application;
import com.apress.prospring6.fifteen.entities.Singer;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@SpringBootTest(classes = Chapter15Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestClient2Test {

    final Logger LOGGER = LoggerFactory.getLogger(RestClient2Test.class);

    @Value("${local.server.port}")
    private int port;

    RestTemplate restTemplate = new RestTemplate();

    private String uriSinger2Root() {
        return "http://localhost:" + port + "/singer2/";
    }

    @Test
    public void testPositiveFindById() throws URISyntaxException {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        RequestEntity<HttpHeaders> req = new RequestEntity<>(headers, HttpMethod.GET, new URI(uriSinger2Root() + 1));
        LOGGER.info("--> Testing retrieve a singer by id : 1");
        ResponseEntity<Singer> response = restTemplate.exchange(req, Singer.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getHeaders().get(HttpHeaders.CONTENT_TYPE)).contains(MediaType.APPLICATION_JSON_VALUE));
        assertNotNull(response.getBody());
    }

    @Test
    public void testNegativeFindById() throws URISyntaxException {
        LOGGER.info("--> Testing retrieve a singer by id : 99");
        RequestEntity<HttpHeaders> req = new RequestEntity<>(HttpMethod.GET, new URI(uriSinger2Root() + 99));

        assertThrowsExactly(HttpClientErrorException.NotFound.class, () -> restTemplate
                .exchange(req, HttpStatus.class));
    }
}
