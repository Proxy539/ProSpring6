package com.apress.prospring6.twenty.boot.controller;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

@RestController
public class HomeController implements ApplicationContextAware {

    private ApplicationContext ctx;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ctx = applicationContext;
    }

    //The response payload for this request will be rendered in JSON
    @RequestMapping(value = "/bean", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> getBeanNames() {
        List<String> beans = Arrays.stream(ctx.getBeanDefinitionNames())
                .sorted()
                .toList();

        return Flux.fromIterable(beans).delayElements(Duration.ofMillis(200));
    }
}
