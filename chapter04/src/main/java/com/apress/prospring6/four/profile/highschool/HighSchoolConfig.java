package com.apress.prospring6.four.profile.highschool;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.apress.prospring6.four.profile.FoodProviderService;

@Configuration
@Profile("highschool")
public class HighSchoolConfig {

    @Bean
    public FoodProviderService foodProviderService() {
        return new FoodProviderServiceImpl();
    }

}
