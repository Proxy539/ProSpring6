package com.apress.prospring6.twelve.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.util.ErrorHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@ComponentScan(basePackages = {"com.apress.prospring6.twelve.spring"})
@EnableScheduling
public class TaskSchedulingConfig4 {

    @Bean
    TaskScheduler taskScheduler() {
        var tpts = new ThreadPoolTaskScheduler();
        tpts.setPoolSize(3);
        tpts.setThreadNamePrefix("tsc4-");
        tpts.setErrorHandler(new LoggingErrorHandler("tsc4"));
        tpts.setRejectedExecutionHandler(new RejectedTaskHandler());
        return tpts;
    }
}

class LoggingErrorHandler implements ErrorHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingErrorHandler.class);
    private final String name;

    public LoggingErrorHandler(String name) {
        this.name = name;
    }

    @Override
    public void handleError(Throwable t) {
        LOGGER.error("[{}]: task failed because {}", name, t.getCause(), t);
    }
}

class RejectedTaskHandler implements RejectedExecutionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RejectedTaskHandler.class);
    private Map<Runnable, Integer> rejectedTasks = new ConcurrentHashMap<>();

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        LOGGER.info(" >> check for resubmission.");
        boolean submit = true;

        if (rejectedTasks.containsKey(r)) {
            int submittedCnt = rejectedTasks.get(r);
            if (submittedCnt > 5) {
                submit = false;
            } else {
                rejectedTasks.put(r, rejectedTasks.get(r) + 1);
            }
        } else {
            rejectedTasks.put(r, 1);
        }

        if (submit) {
            executor.execute(r);
        } else {
            LOGGER.error(" >> Task {} cannot be re-submitted.", r.toString());
        }
    }
}
