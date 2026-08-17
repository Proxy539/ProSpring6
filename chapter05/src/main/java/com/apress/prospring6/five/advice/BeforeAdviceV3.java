package com.apress.prospring6.five.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class BeforeAdviceV3 {

    private static Logger LOGGER = LoggerFactory.getLogger(BeforeAdviceV3.class);

    @Pointcut("execution(* com.apress.prospring6.five..sing*(com.apress.prospring6.five.common.Guitar))")
    public void singExecution() {

    }

    @Pointcut("bean(john*)")
    public void isJohn() {

    }

    @Before("singExecution() && isJohn()")
    public void simpleBeforeAdvice(JoinPoint joinPoint) {
        var signature = (MethodSignature) joinPoint.getSignature();
        LOGGER.info("> Executing: {} from {}", signature.getName(), signature.getDeclaringTypeName());
    }
}
