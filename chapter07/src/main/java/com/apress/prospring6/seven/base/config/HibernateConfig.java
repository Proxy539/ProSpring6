package com.apress.prospring6.seven.base.config;

import org.hibernate.cfg.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.orm.jpa.hibernate.HibernateTransactionManager;
import org.springframework.orm.jpa.hibernate.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Import(BasicDataSourceCfg.class)
@Configuration
@ComponentScan(basePackages = {"com.apress.prospring6.seven.base"})
@EnableTransactionManagement
public class HibernateConfig {
    private static Logger LOGGER = LoggerFactory.getLogger(HibernateConfig.class);

    @Autowired
    DataSource dataSource;

    @Bean
    public Properties hibernateProperties() {
        Properties hibernateProp = new Properties();

        hibernateProp.put(Environment.HBM2DDL_AUTO, "none");
        hibernateProp.put(Environment.FORMAT_SQL, false);
        hibernateProp.put(Environment.USE_SQL_COMMENTS, false);
        hibernateProp.put(Environment.SHOW_SQL, false);
        hibernateProp.put(Environment.MAX_FETCH_DEPTH, 3);
        hibernateProp.put(Environment.STATEMENT_BATCH_SIZE, 10);
        hibernateProp.put(Environment.STATEMENT_FETCH_SIZE, 50);

        return hibernateProp;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setPackagesToScan("com.apress.prospring6.seven.base.entities");
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        var transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }
}
