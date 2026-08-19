package com.apress.prospring6.six;

import com.apress.prospring6.six.repo.SingerRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.AbstractEnvironment;

@SpringBootApplication
public class Chapter06Application {
    private static Logger logger = LoggerFactory.getLogger(Chapter06Application.class);

    public static void main(String[] args) {
        System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "dev");

        var ctx = SpringApplication.run(Chapter06Application.class, args);

        var repo = ctx.getBean(SingerRepo.class);

        repo.findAll().forEach(singer -> logger.info(singer.toString()));
    }
}
