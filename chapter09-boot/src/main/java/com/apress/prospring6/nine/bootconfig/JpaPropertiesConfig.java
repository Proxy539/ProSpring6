package com.apress.prospring6.nine.bootconfig;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

// AlbumRepoImpl (reused from chapter09) reads
// #{jpaProperties.get('hibernate.jdbc.batch_size')} via @Value, which needs a bean
// literally named "jpaProperties" - Spring Boot's own JpaProperties auto-configuration
// bean isn't published under that name, so bind spring.jpa.properties.* (already set in
// application.yaml) onto one here.
@Configuration
public class JpaPropertiesConfig {

    @Bean
    @ConfigurationProperties("spring.jpa.properties")
    public Properties jpaProperties() {
        return new Properties();
    }
}
