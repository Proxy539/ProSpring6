package com.apress.prospring6.five.pointcut;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleAroundAdvice implements MethodInterceptor {

    private static Logger logger = LoggerFactory.getLogger(SimpleAroundAdvice.class);

    @Override
    public @Nullable Object invoke(MethodInvocation invocation) throws Throwable {
        logger.debug(">> Invoking " + invocation.getMethod().getName());
        Object retVal = invocation.proceed();
        logger.debug(">> Done");

        return retVal;
    }
}
