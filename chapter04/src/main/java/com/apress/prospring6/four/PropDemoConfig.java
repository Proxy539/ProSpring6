package com.apress.prospring6.four;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.support.ResourcePropertySource;

import jakarta.annotation.PostConstruct;

@Configuration
@PropertySource("classpath:application.properties")
public class PropDemoConfig {
    @Autowired
    StandardEnvironment environment;

    @PostConstruct
    void configPriority() {
        ResourcePropertySource rps = (ResourcePropertySource) environment.getPropertySources().stream()
                .filter(ps -> ps instanceof ResourcePropertySource)
                .findAny()
                .orElse(null);

        environment.getPropertySources().addFirst(rps);
    }

    @Bean
    AppProperty appProperty() {
        return new AppProperty();
    }

}
