package com.apress.prospring6.twelve.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Async2Demo {

    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncDemo.class);

    public static void main(String[] args) {
        try (var ctx = new AnnotationConfigApplicationContext(Async2Config.class)) {
            var asyncService = ctx.getBean("asyncService", AsyncService.class);
            //code to invoke tasks is omitted fur duplicate
        }
    }
}
