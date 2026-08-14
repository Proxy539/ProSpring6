package com.apress.prospring6.four.aware;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
class AwareConfig {

    @Bean
    public NamedSinger johnMayer() {
        return new NamedSinger();
    }

}

public class AwareDemo {
    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext(AwareConfig.class);
        ctx.registerShutdownHook();

        var singer = ctx.getBean(NamedSinger.class);
        singer.sing();
    }
}
