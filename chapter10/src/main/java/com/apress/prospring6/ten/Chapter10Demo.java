package com.apress.prospring6.ten;

import com.apress.prospring6.ten.config.DataJpaCfg;
import com.apress.prospring6.ten.service.SingerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Chapter10Demo {

    private static final Logger LOGGER = LoggerFactory.getLogger(Chapter10Demo.class);

    public static void main(String... args) {
        try (var ctx = new AnnotationConfigApplicationContext(DataJpaCfg.class)) {
            var service = ctx.getBean(SingerService.class);

            LOGGER.info(" ---- Listing singers:");
            service.findAll().forEach(s -> LOGGER.info(s.toString()));
        }
    }
}
