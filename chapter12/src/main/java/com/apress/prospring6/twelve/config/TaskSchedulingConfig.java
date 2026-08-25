package com.apress.prospring6.twelve.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@ComponentScan(basePackages = {"com.apress.prospring6.twelve"})
@EnableScheduling
public class TaskSchedulingConfig {
}
