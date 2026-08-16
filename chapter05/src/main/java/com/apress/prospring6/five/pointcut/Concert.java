package com.apress.prospring6.five.pointcut;

import com.apress.prospring6.five.manual.Performance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class Concert implements Performance {

    private static Logger logger = LoggerFactory.getLogger(Concert.class);

    @Override
    public void execute() {
        logger.info("... La la la la la la ...");
        try {
            Thread.sleep(Duration.ofMillis(2000).toMillis());
        } catch (InterruptedException e) {

        }
    }
}
