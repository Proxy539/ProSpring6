package com.apress.prospring6.seventeen;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityCfg3 {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //request configuration omitted
        return http.build();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManagerBuilder authenticationManagerBuilder(ObjectPostProcessor<Object> objectPostProcessor,
                                                              DataSource dataSource) {
        var authenticationManagerBuilder = new AuthenticationManagerBuilder(objectPostProcessor);

        final String findUserQuery = """
                    select username, password, enabled
                    from users where username = ?
                """;
        final String findRoles = """
                    select username, authority from authorities
                    where username = ?
                """;

        try {
            authenticationManagerBuilder.jdbcAuthentication()
                    .dataSource(dataSource)
                    .passwordEncoder(encoder())
                    .usersByUsernameQuery(findUserQuery)
                    .authoritiesByUsernameQuery(findRoles);

            return authenticationManagerBuilder;
        } catch (Exception e) {
            throw new RuntimeException("Could not initialize 'AuthenticationManagerBuilder'");
        }
    }
}
