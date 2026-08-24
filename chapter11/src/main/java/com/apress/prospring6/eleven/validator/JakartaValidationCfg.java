package com.apress.prospring6.eleven.validator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

// a bare @ComponentScan here would also pick up the org.springframework.validation.Validator
// @Components in this package (AddressValidator, BloggerWithAddressValidator, ...) that
// SpringValidatorTest registers explicitly - leaving BloggerWithAddressValidator's Validator
// constructor argument ambiguous against LocalValidatorFactoryBean's "validator" bean, which
// also implements org.springframework.validation.Validator. Import only what this config needs.
@Configuration
@Import(SingerValidationService.class)
public class JakartaValidationCfg {

    @Bean
    LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }
}
