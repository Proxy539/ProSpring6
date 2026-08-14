package com.apress.prospring6.four.factory;

import java.security.MessageDigest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class FactoryBeanDemo {

    private static Logger logger = LoggerFactory.getLogger(FactoryBeanDemo.class);

    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext(MessageDigestConfig.class);

        MessageDigestFactoryBean factoryBean = (MessageDigestFactoryBean) ctx.getBean("&shaDigest");
        try {
            MessageDigest shaDigest = factoryBean.getObject();
            logger.info("Explicit use digest bean: {}", shaDigest.digest("Hello World".getBytes()));
        } catch (Exception ex) {
            logger.error("Could not find MessageDigestFactoryBean ", ex);
        }

        ctx.close();
    }

}
