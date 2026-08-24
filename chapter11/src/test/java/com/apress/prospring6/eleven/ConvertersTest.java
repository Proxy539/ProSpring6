package com.apress.prospring6.eleven;

import com.apress.prospring6.eleven.converter.bean.ConverterCfg;
import com.apress.prospring6.eleven.domain.Blogger;
import com.apress.prospring6.eleven.domain.SimpleBlogger;
import com.apress.prospring6.eleven.property.editor.CustomRegistrarCfg;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.convert.ConversionService;

public class ConvertersTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConvertersTest.class);

    @Test
    public void testCustomPropertyEditorRegistrar() {
        try (var ctx = new AnnotationConfigApplicationContext(AppConfig.class, CustomRegistrarCfg.class)) {
            var springBlogger = ctx.getBean("springBlogger", Blogger.class);
            LOGGER.info("SpringBlogger info: {}", springBlogger);

            var awsBlogger = ctx.getBean("awsBlogger", Blogger.class);
            LOGGER.info("AwsBlogger info: {}", awsBlogger);
        }
    }

    @Test
    public void testConvertingToSimpleBlogger() {
        try (var ctx = new AnnotationConfigApplicationContext(AppConfig.class, ConverterCfg.class)) {
            var springBlogger = ctx.getBean("springBlogger", Blogger.class);
            LOGGER.info("SpringBlogger info: {}", springBlogger);

            var conversionService = ctx.getBean(ConversionService.class);
            var simpleBlogger = conversionService.convert(springBlogger, SimpleBlogger.class);
            LOGGER.info("simpleBlogger info: {}", simpleBlogger);
        }
    }
}
