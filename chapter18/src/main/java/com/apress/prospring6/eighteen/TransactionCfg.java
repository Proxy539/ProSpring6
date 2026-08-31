package com.apress.prospring6.eighteen;

import com.apress.prospring6.eighteen.config.BasicDataSourceCfg;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Environment;
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
@EnableJpaRepositories(basePackages = {"com.apress.prospring6.eighteen.repos"})
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.apress.prospring6.eighteen.repos", "com.apress.prospring6.eighteen.services"})
public class TransactionCfg {
    //other configurations omitted

    @Autowired
    DataSource dataSource;

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactoryBean().getObject());
        return transactionManager;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
        var factory = new LocalContainerEntityManagerFactoryBean();

        factory.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        factory.setPackagesToScan("com.apress.prospring6.eighteen.entities");
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
        jpaProps.put(Environment.GENERATE_STATISTICS, true);
        jpaProps.put("hibernate.jmx.enabled", true);
        jpaProps.put("hibernate.jmx.usePlatformServer", true);
        jpaProps.put(Environment.SESSION_FACTORY_NAME, "sessionFactory");

        return jpaProps;
    }

    @Bean
    public SessionFactory sessionFactory(EntityManagerFactory entityManagerFactory) {
        return entityManagerFactory.unwrap(SessionFactory.class);
    }
}
