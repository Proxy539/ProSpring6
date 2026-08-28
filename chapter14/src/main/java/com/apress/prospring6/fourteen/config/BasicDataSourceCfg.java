package com.apress.prospring6.fourteen.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:db/jdbc.properties")
public class BasicDataSourceCfg {
    private static final Logger LOGGER = LoggerFactory.getLogger(BasicDataSourceCfg.class);

    @Value("${jdbc.driverClassName}")
    private String driverClassName;

    @Value("${jdbc.url}")
    private String url;

    @Value("${jdbc.username}")
    private String username;

    @Value("${jdbc.password}")
    private String password;

    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        try {
            var hc = new HikariConfig();
            hc.setJdbcUrl(url);
            hc.setDriverClassName(driverClassName);
            hc.setUsername(username);
            hc.setPassword(password);
            var dataSource = new HikariDataSource(hc);
            dataSource.setMaximumPoolSize(25);
            return dataSource;
        } catch (Exception e) {
            LOGGER.error("Hikari DataSource bean cannot be created!", e);
            return null;
        }
    }
}
