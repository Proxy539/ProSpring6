package com.apress.prospring6.ten.config;

import org.hibernate.cfg.Environment;
import org.hibernate.envers.configuration.EnversSettings;
import org.hibernate.envers.strategy.internal.ValidityAuditStrategy;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Import(BasicDataSourceCfg.class)
@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.apress.prospring6.ten"})
@EnableJpaRepositories("com.apress.prospring6.ten.repos")
public class EnversConfig {
    @Autowired
    DataSource dataSource;

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        var factory = new LocalContainerEntityManagerFactoryBean();
        factory.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        factory.setPackagesToScan("com.apress.prospring6.ten.entities");
        factory.setDataSource(dataSource);
        factory.setJpaProperties(jpaProperties());
        factory.setJpaVendorAdapter(jpaVendorAdapter());
        return factory;
    }

    @Bean
    public Properties jpaProperties() {
        Properties jpaProps = new Properties();
        jpaProps.put(Environment.HBM2DDL_AUTO, "none");
        jpaProps.put(Environment.FORMAT_SQL, false);
        jpaProps.put(Environment.STATEMENT_BATCH_SIZE, 30);
        jpaProps.put(Environment.USE_SQL_COMMENTS, false);
        jpaProps.put(Environment.SHOW_SQL, false);

        //Properties for Hibernate Envers
        jpaProps.put(EnversSettings.AUDIT_TABLE_SUFFIX, "_H");
        jpaProps.put(EnversSettings.REVISION_FIELD_NAME, "AUDIT_REVISION");
        jpaProps.put(EnversSettings.REVISION_TYPE_FIELD_NAME, "ACTION_TYPE");
        jpaProps.put(EnversSettings.AUDIT_STRATEGY, ValidityAuditStrategy.class.getName());
        jpaProps.put(EnversSettings.AUDIT_STRATEGY_VALIDITY_END_REV_FIELD_NAME, "AUDIT_REVISION_END");
        jpaProps.put(EnversSettings.AUDIT_STRATEGY_VALIDITY_STORE_REVEND_TIMESTAMP, "true");
        jpaProps.put(EnversSettings.AUDIT_STRATEGY_VALIDITY_REVEND_TIMESTAMP_FIELD_NAME, "AUDIT_REVISION_END_TS");
        return jpaProps;
    }
}
