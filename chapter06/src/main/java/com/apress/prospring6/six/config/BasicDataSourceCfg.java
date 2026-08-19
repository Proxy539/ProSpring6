package com.apress.prospring6.six.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;
import java.util.concurrent.ExecutionException;

@Configuration
@PropertySource("classpath:db/jdbc.properties")
public class BasicDataSourceCfg {

    private static Logger LOGGER = LoggerFactory.getLogger(BasicDataSourceCfg.class);

    @Value("${db.driverClassName}")
    private String driverClassName;

    @Value(("${db.url}"))
    private String url;

    @Value("${db.username}")
    private String username;

    @Value("${db.password}")
    private String password;

    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        try {
            var dataSource = new BasicDataSource();
            dataSource.setDriverClassName(driverClassName);
            dataSource.setUrl(url);
            dataSource.setUsername(username);
            dataSource.setPassword(password);
            return dataSource;
        } catch (Exception e) {
            LOGGER.error("DBCP DataSource bean cannot be created!", e);
            return null;
        }
    }
}
