package com.apress.prospring6.eighteen;

import com.apress.prospring6.eighteen.entities.Singer;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

public class RestClientTest {

    final Logger LOGGER = LoggerFactory.getLogger(RestClientTest.class);

    private static final String URI_SINGER_ROOT = "http://localhost:8080/ch18/singer/";
    private static final String URI_SINGER_WITH_ID = "http://localhost:8080/ch18/singer/{id}";

    RestTemplate restTemplate = new RestTemplate();

    @RepeatedTest(10)
    @Test
    public void testCreate() {
        LOGGER.info("--> Testing create singer");
        Singer singerNew = new Singer();
        singerNew.setFirstName("TEST" + System.currentTimeMillis());
        singerNew.setLastName("Singe" + System.currentTimeMillis());
        singerNew.setBirthDate(LocalDate.now());
        singerNew = restTemplate.postForObject(URI_SINGER_ROOT, singerNew, Singer.class);
        LOGGER.info("Singer created successfully: " + singerNew);
    }

    @Test
    public void testDelete() {
        LOGGER.info("--> Deleting singers with id > 15");
        for (int i = 16; i < 70; i++) {
            try {
                restTemplate.delete(URI_SINGER_WITH_ID, i);
            } catch (Exception e) {
                //no need to treat
            }
        }
    }
}
