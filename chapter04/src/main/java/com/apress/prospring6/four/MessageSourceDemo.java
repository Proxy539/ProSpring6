package com.apress.prospring6.four;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
class MessageSourceConfig {
    @Bean
    public MessageSource messageSource() {
        var messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("labels");
        return messageSource;
    }
}

public class MessageSourceDemo {
    private static Logger logger = LoggerFactory.getLogger(MessageSourceDemo.class);

    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext(MessageSourceConfig.class);

        Locale english = Locale.ENGLISH;
        Locale ukranian = new Locale("uk", "UA");

        logger.info(ctx.getMessage("msg", null, english));
        logger.info(ctx.getMessage("msg", null, ukranian));
        logger.info(ctx.getMessage("nameMsg", new Object[] { "Iuliana", "Cosmina" }, english));
        logger.info(ctx.getMessage("naemMsg", new Object[] { "Iuliana", "Cosmina" }, ukranian));

        ctx.close();

    }
}
