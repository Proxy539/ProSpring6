package com.apress.prospring6.twelve;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

public class SimpleAsyncTaskExecutorDemo {

    public static void main(String[] args) throws IOException {
        try (var ctx = new AnnotationConfigApplicationContext(AppConfig.class, RandomStringPrinter.class)) {
            var printer = ctx.getBean(RandomStringPrinter.class);
            printer.executeTask();

            System.in.read();
        }
    }
}
