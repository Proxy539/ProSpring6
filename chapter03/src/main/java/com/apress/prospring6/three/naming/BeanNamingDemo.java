package com.apress.prospring6.three.naming;

import java.util.Arrays;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

public class BeanNamingDemo {

    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext(BeanNamingCfg.class);
        Arrays.stream(ctx.getBeanDefinitionNames()).forEach(beanName -> System.out.println(beanName));
    }

}

@Configuration
@ComponentScan
class BeanNamingCfg {
    @Bean
    public SimpleBean anotherSimpleBean() {
        return new SimpleBean();
    }
}
