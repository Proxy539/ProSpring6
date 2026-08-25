package com.apress.prospring6.twelve;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskExecutor;

import java.util.UUID;

public class RandomStringPrinter {

    private final Logger logger = LoggerFactory.getLogger(RandomStringPrinter.class);

    private final TaskExecutor taskExecutor;

    public RandomStringPrinter(TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    public void executeTask() {
        for (int i = 0; i < 10; ++i) {
            final int index = i;
            taskExecutor.execute(() -> logger.info("{}: {}", index, UUID.randomUUID().toString().substring(0, 8)));
        }
    }
}
