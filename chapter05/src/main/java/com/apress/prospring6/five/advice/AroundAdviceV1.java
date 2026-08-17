package com.apress.prospring6.five.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AroundAdviceV1 {

    private static Logger LOGGER = LoggerFactory.getLogger(AroundAdviceV1.class);

    @Pointcut("execution(* com.apress.prospring6.five..sing*(com.apress.prospring6.five.common.Guitar))")
    public void singExecution() {

    }

    @Around("singExecution()")
    public Object simpleAroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
        var signature = (MethodSignature) pjp.getSignature();

        LOGGER.info("> Before Executing: {} from {}", signature.getName(), signature.getDeclaringTypeName());

        Object retVal = pjp.proceed();

        LOGGER.info("> After Executing: {} from {}", signature.getName(), signature.getDeclaringTypeName());

        return retVal;
    }
}
