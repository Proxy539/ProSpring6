package com.apress.prospring6.five.boot;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* com.apress.prospring6.five.common.GrammyGuitarist.*(..))")
    public void before(JoinPoint jp) {
        LOGGER.info("> Executing: {}", jp.getSignature().getName());
    }
}
