package com.apress.prospring6.sixteen.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EntityScan(basePackages = {"com.apress.prospring6.sixteen.entities"})
@EnableJpaRepositories("com.apress.prospring6.sixteen.repos")
@EnableTransactionManagement
@SpringBootApplication(scanBasePackages = "com.apress.prospring6.sixteen")
public class Chapter16NativeApplication {

    public static void main(String[] args) {
        SpringApplication.run(Chapter16NativeApplication.class, args);
    }
}
