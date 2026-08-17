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
public class BeforeAdviceV2 {

    private static Logger LOGGER = LoggerFactory.getLogger(BeforeAdviceV2.class);

    @Pointcut("execution(* com.apress.prospring6.five..sing*(com.apress.prospring6.five.common.Guitar))")
    public void singExecution() {

    }

    @Before("singExecution()")
    public void simpleBeforeAdvice(JoinPoint joinPoint) {
        var signature = (MethodSignature) joinPoint.getSignature();
        LOGGER.info("> Executing: {} from {}", signature.getName(), signature.getDeclaringTypeName());
    }
}
