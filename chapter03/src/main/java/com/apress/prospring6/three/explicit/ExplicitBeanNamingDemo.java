package com.apress.prospring6.three.explicit;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

public class ExplicitBeanNamingDemo {

    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext(BeanNamingCfg.class);

        var simpleBeans = ctx.getBeansOfType(SimpleBean.class);
        simpleBeans.forEach((k, v) -> {
            var alises = ctx.getAliases(k);
            if (alises.length > 0) {
                System.out.println("Aliases for " + k);
            }
        });
    }

}

@Configuration
@ComponentScan
class BeanNamingCfg {
    @Bean
    public SimpleBean simpleBean2() {
        return new SimpleBean();
    }

    @Bean
    public SimpleBean simpleBean3() {
        return new SimpleBean();
    }
}

@Component(value = "simpleBeanOne")
class SimpleBean {

}
