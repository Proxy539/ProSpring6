package com.apress.prospring6.eleven.converter.bean;

import com.apress.prospring6.eleven.domain.SimpleBlogger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;

import java.util.HashSet;

@Configuration
@ComponentScan
public class ConverterCfg {

    @Bean
    public ConversionServiceFactoryBean conversionService() {
        var conversionServiceFactoryBean = new ConversionServiceFactoryBean();
        var convs = new HashSet<>();
        convs.add(new LocalDateConverter());
        convs.add(new SimpleBlogger.BloggerToSimpleBloggerConverter());
        conversionServiceFactoryBean.setConverters(convs);
        conversionServiceFactoryBean.afterPropertiesSet();

        return conversionServiceFactoryBean;
    }
}
