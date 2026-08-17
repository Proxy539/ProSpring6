package com.apress.prospring6.five.advance;

import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

public class SimpleBeforeAdvance implements MethodBeforeAdvice {

    private static Logger LOGGER = LoggerFactory.getLogger(SimpleBeforeAdvance.class);

    @Override
    public void before(Method method, @Nullable Object[] args, @Nullable Object target) throws Throwable {
        LOGGER.info("Before method: {}", method);
    }
}
