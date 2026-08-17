package com.apress.prospring6.five.advice;

import com.apress.prospring6.five.common.Guitar;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeforeAdviceV4 {

    private static Logger LOGGER = LoggerFactory.getLogger(BeforeAdviceV4.class);

    @Pointcut("execution(* com.apress.prospring6.five..sing*(com.apress.prospring6.five.common.Guitar)) && args(value)")
    public void singExecution(Guitar value) {

    }

    @Pointcut("bean(john*)")
    public void isJohn() {

    }

    @Before(value = "singExecution(guitar) && isJohn()", argNames = "joinPoint,guitar")
    public void simpleBeforeAdvice(JoinPoint joinPoint, Guitar guitar) {
        var signature = (MethodSignature) joinPoint.getSignature();
        LOGGER.info("> Executing: {} from {}", signature.getName(), signature.getDeclaringTypeName());
    }
}
