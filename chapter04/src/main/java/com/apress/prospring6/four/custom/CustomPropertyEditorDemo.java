package com.apress.prospring6.four.custom;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.CustomEditorConfigurer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
class Person {

    private FullName name;

    @Value("John Mayer")
    public void setName(FullName name) {
        this.name = name;
    }

    public FullName getName() {
        return name;
    }
}

@Configuration
@ComponentScan
class CustomPropertyEditorCfg {

    @Bean
    CustomEditorConfigurer customEditorConfigurer() {
        var cust = new CustomEditorConfigurer();
        cust.setCustomEditors(Map.of(FullName.class, NamePropertyEditor.class));
        return cust;
    }
}

public class CustomPropertyEditorDemo {
    private static Logger logger = LoggerFactory.getLogger(CustomPropertyEditorDemo.class);

    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext(CustomPropertyEditorCfg.class);

        var person = ctx.getBean(Person.class, "person");
        logger.info("Person full name = {}", person.getName());

        ctx.close();
    }
}
