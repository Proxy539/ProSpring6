package com.apress.prospring6.nine.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

@Import(BasicDataSourceCfg.class)
@Configuration
@ComponentScan(basePackages = {"com.apress.prospring6.nine.repos", "com.apress.prospring6.nine.programmatic"})
public class ProgrammaticTransactionCfg {

    @Bean
    public TransactionTemplate transactionTemplate() {
        TransactionTemplate tt = new TransactionTemplate();
        tt.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        tt.setTimeout(30);
        tt.setTransactionManager(transactionManager());
        return tt;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(transactionManager.getEntityManagerFactory());
        return transactionManager;
    }
}
