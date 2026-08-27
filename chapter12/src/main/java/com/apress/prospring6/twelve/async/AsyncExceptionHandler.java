package com.apress.prospring6.twelve.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

public class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncExceptionHandler.class);

    @Override
    public void handleUncaughtException(Throwable t, Method method, Object... obj) {
        LOGGER.error("[{}]: task method '{}' failed because {}", Thread.currentThread(),
                method.getName(), t.getMessage(), t);
    }
}
