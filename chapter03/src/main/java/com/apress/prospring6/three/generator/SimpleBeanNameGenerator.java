package com.apress.prospring6.three.generator;

import java.util.Locale;
import java.util.UUID;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;

public class SimpleBeanNameGenerator extends AnnotationBeanNameGenerator {

    @Override
    protected String buildDefaultBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
        var beanName = definition.getBeanClassName().substring(definition.getBeanClassName().lastIndexOf(".") + 1)
                .toLowerCase(Locale.ROOT);
        var uid = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        return beanName + "-" + uid;
    }

}
