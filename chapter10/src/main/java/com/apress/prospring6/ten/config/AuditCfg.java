package com.apress.prospring6.ten.config;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@EnableJpaAuditing
@Configuration
public class AuditCfg {

    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> Optional.of("prospring6-" + RandomStringUtils.secure().next(6, true, true));
    }
}
