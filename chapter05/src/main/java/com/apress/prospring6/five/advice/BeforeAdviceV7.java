package com.apress.prospring6.five.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@Scope("prototype")
@Aspect("perthis(singExecution())")
public class BeforeAdviceV7 {
    private static Logger LOGGER = LoggerFactory.getLogger(BeforeAdviceV7.class);

    public BeforeAdviceV7() {
        LOGGER.info("BeforeAdviceV7 creation time: {}", Instant.now());
    }

    @Pointcut("execution(* com.apress.prospring6.five..sing*(com.apress.prospring6.five.common.Guitar))")
    public void singExecution() {

    }

    @Before("singExecution()")
    public void simpleBeforeAdvice(JoinPoint joinPoint) {
        var signature = (MethodSignature) joinPoint.getSignature();
        LOGGER.info("> Executing: {} from {}", signature.getName(), signature.getDeclaringTypeName());
    }

}
