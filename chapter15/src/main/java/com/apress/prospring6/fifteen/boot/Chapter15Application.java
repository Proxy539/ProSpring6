package com.apress.prospring6.fifteen.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EntityScan(basePackages = {"com.apress.prospring6.fifteen.entities"})
@EnableJpaRepositories("com.apress.prospring6.fifteen.repos")
@EnableTransactionManagement
// scanBasePackages deliberately lists the Boot-demo subpackages only - NOT the shared root
// package - because WebConfig/WebInitializer (siblings in the root package) belong to the
// classic, non-Boot WAR demo and redeclare beans that Boot's own autoconfiguration already
// provides, which fails context startup with a BeanDefinitionOverrideException if picked up here too.
@SpringBootApplication(scanBasePackages = {
        "com.apress.prospring6.fifteen.boot",
        "com.apress.prospring6.fifteen.controllers",
        "com.apress.prospring6.fifteen.problem",
        "com.apress.prospring6.fifteen.services"
})
public class Chapter15Application {

    public static void main(String[] args) {
        System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "dev");
        SpringApplication.run(Chapter15Application.class, args);
    }
}
