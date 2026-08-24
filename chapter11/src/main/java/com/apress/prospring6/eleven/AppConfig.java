package com.apress.prospring6.eleven;

import com.apress.prospring6.eleven.domain.Blogger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.net.URL;
import java.time.LocalDate;

@PropertySource("classpath:blogger.properties")
@Configuration
public class AppConfig {

    @Bean
    public Blogger awsBlogger(@Value("Alex") String firstName,
                              @Value("DeBrie") String lastName,
                              @Value("https://www.alexdebrie.com")URL personalSite,
                              @Value("1980-01-02")LocalDate birthDate) throws Exception {
        return new Blogger(firstName, lastName, birthDate, personalSite);
    }

    @Bean
    public Blogger springBlogger(@Value("${springBlogger.firstName}") String firstName,
                                 @Value("${springBlogger.lastName}") String lastName,
                                 @Value("${springBlogger.personalSite}") URL personalSite,
                                 @Value("${springBlogger.birthDate}") LocalDate birthDate) throws Exception {
        return new Blogger(firstName, lastName, birthDate, personalSite);
    }
}
