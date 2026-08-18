package com.apress.prospring6.five.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Chapter05BootApplication {
    private static Logger logger = LoggerFactory.getLogger(Chapter05BootApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(Chapter05BootApplication.class, args);
    }
}
