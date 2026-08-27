package com.apress.prospring6.twelve.async;

import org.jspecify.annotations.Nullable;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
@ComponentScan
public class Async2Config implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor() {
        var tpts = new ThreadPoolTaskExecutor();
        tpts.setCorePoolSize(2);
        tpts.setMaxPoolSize(10);
        tpts.setThreadNamePrefix("tpte2-");
        tpts.setQueueCapacity(5);
        tpts.initialize();
        return tpts;
    }

    @Bean
    public AsyncService asyncService() {
        return new AsyncServiceImpl();
    }

    @Override
    public @Nullable AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new AsyncExceptionHandler();
    }
}
