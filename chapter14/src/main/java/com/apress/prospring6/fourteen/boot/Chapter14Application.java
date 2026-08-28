package com.apress.prospring6.fourteen.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EntityScan(basePackages = {"com.apress.prospring6.fourteen.entities"})
@EnableJpaRepositories("com.apress.prospring6.fourteen.repos")
@EnableTransactionManagement
// scanBasePackages deliberately lists the Boot-demo subpackages only - NOT the shared root
// package - because WebConfig/WebInitializer (siblings in the root package) belong to the
// classic, non-Boot WAR demo and redeclare beans (e.g. localeResolver) that Boot's own
// autoconfiguration already provides, which fails context startup with a
// BeanDefinitionOverrideException if they're picked up here too.
@SpringBootApplication(scanBasePackages = {
        "com.apress.prospring6.fourteen.boot",
        "com.apress.prospring6.fourteen.controllers",
        "com.apress.prospring6.fourteen.problem",
        "com.apress.prospring6.fourteen.services"
})
public class Chapter14Application {

    public static void main(String[] args) {
        System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "dev");
        SpringApplication.run(Chapter14Application.class, args);
    }
}
