package com.apress.prospring6.eleven;

import com.apress.prospring6.eleven.domain.Blogger;
import com.apress.prospring6.eleven.formatter.factory.ApplicationConversionServiceFactoryBean;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class FormattersTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(FormattersTest.class);

    @Test
    public void testFormattingFactoryService() {
        try (var ctx = new AnnotationConfigApplicationContext(AppConfig.class,
                ApplicationConversionServiceFactoryBean.class)) {
            var springBlogger = ctx.getBean("springBlogger", Blogger.class);
            LOGGER.info("SpringBlogger info: {}", springBlogger);

            var awsBlogger = ctx.getBean("awsBlogger", Blogger.class);
            LOGGER.info("AwsBlogger info: {}", awsBlogger);
        }
    }
}
