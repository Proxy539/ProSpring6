package com.apress.prospring6.four.all;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

class Dependency { // very simple bean type

}

class MultiInit implements InitializingBean {
    private static Logger logger = LoggerFactory.getLogger(MultiInit.class);
    private Dependency dependency;

    public MultiInit() {
        logger.info("1. Calling constructor for bean of type {}", MultiInit.class);
    }

    public Dependency getDependency() {
        return dependency;
    }

    @Autowired
    public void setDependency() {
        logger.info("2. Calling setDependency for bean of type {}.", MultiInit.class);
    }

    @PostConstruct
    private void postConstruct() throws Exception {
        logger.info("3. Calling PostConstruct() for bean of type {}.", MultiInit.class);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("4. Calling afterPropertiesSet() for bean type {}.", MultiInit.class);
    }

    private void initMe() throws Exception {
        logger.info("5. Calling initMethod() for bean of type {}.", MultiInit.class);
    }
}

@Configuration
class MultiInitConfiguration {

    @Bean
    Dependency dependency() {
        return new Dependency();
    }

    @Bean(initMethod = "initMe")
    public MultiInit multiInitBean() {
        return new MultiInit();
    }
}

public class AllInitMethodsDemo {
    public static void main(String[] args) {
        new AnnotationConfigApplicationContext(MultiInitConfiguration.class);
    }
}
