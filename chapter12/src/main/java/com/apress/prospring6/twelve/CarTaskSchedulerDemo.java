package com.apress.prospring6.twelve;

import com.apress.prospring6.twelve.config.TaskSchedulingConfig;
import com.apress.prospring6.twelve.config.TaskSchedulingConfig2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;

import java.io.IOException;

public class CarTaskSchedulerDemo {
    private static final Logger LOGGER = LoggerFactory.getLogger(CarTaskSchedulerDemo.class);

    public static void main(String[] args) throws IOException {
        try (var ctx = new AnnotationConfigApplicationContext(TaskSchedulingConfig2.class)) {
            try {
                var taskScheduler = ctx.getBean("taskExecutor");
                LOGGER.info(">>>> Task 'taskScheduler' found: {}", taskScheduler.getClass());
            } catch (NoSuchBeanDefinitionException nbd) {
                LOGGER.debug("No 'taskScheduler' configured!");
            }
            System.in.read();
        }
    }
}
