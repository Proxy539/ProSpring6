package com.apress.prospring6.five.advice;

import com.apress.prospring6.five.common.Guitar;
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
public class AroundAdviceV2 {

    private static Logger LOGGER = LoggerFactory.getLogger(AroundAdviceV2.class);

    @Pointcut("execution(* com.apress.prosprin6.five..sing*(com.apress.prospring6.five.common.Guitar)) && args(value)")
    public void singExecution(Guitar value) {

    }

    @Around(value = "singExecution(guitar)", argNames = "pjp,guitar")
    public Object simpleAroundAdvice(ProceedingJoinPoint pjp, Guitar guitar) throws Throwable {
        var signature = (MethodSignature) pjp.getSignature();

        LOGGER.info("> Before Executing: {} from {}", signature.getName(), signature.getDeclaringTypeName());

        Object retVal = pjp.proceed();

        LOGGER.info("> After Executing: {} from {}", signature.getName(), signature.getDeclaringTypeName());

        return retVal;
    }
}
