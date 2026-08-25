package com.apress.prospring6.twelve.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;


@Configuration
@ComponentScan(basePackages = {"com.apress.prospring6.twelve.spring"})
@EnableScheduling
public class TaskSchedulingConfig2 implements SchedulingConfigurer {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskSchedulingConfig2.class);

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskExecutor());
    }

    @Bean(destroyMethod = "shutdown")
    public Executor taskExecutor() {
        var tpts = new ThreadPoolTaskExecutor();
        tpts.setPoolSize(3);
        tpts.setThradNamePrefix("tsc2-");
        return tpts;
    }
}
