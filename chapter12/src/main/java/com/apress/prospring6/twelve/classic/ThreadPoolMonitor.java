package com.apress.prospring6.twelve.classic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadPoolExecutor;

public class ThreadPoolMonitor implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadPoolMonitor.class);

    protected ThreadPoolExecutor executor;
    protected int printInterval = 200;

    @Override
    public void run() {
        try {
            while (executor.getActiveCount() > 0) {
                monitorThreadPool();
                Thread.sleep(printInterval);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    private void monitorThreadPool() {
        String strBuff = "CurrentPoolSize: "  + executor.getPoolSize() +
                " - CorePoolSize: " + executor.getCorePoolSize() +
                " - MaximumPoolSize: " + executor.getMaximumPoolSize() +
                " - ActiveTaskCount: " + executor.getActiveCount() +
                " - CompletedTaskCount: " + executor.getCompletedTaskCount() +
                " - TotalTaskCount: " + executor.getTaskCount() +
                " - isTerminated: " + executor.isTerminated();

        LOGGER.debug(strBuff);
    }

    public void setExecutor(ThreadPoolExecutor executor) {
        this.executor = executor;
    }
}
