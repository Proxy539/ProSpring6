package com.apress.prospring6.five.advice;

import com.apress.prospring6.five.common.Guitar;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Aspect
public class AfterAdviceV1 {

    private static Logger LOGGER = LoggerFactory.getLogger(AfterAdviceV1.class);

    @Pointcut("execution(* com.apress.prospring6.five..PretentiosGuitarist.sing*(com.apress.prosprin6.five.common.Guitar)) && args(value)")
    public void singExecution(Guitar value) {

    }

    @After(value = "singExecution(guitar)", argNames = "joinPoint,guitar")
    public void simpleAfterAdvice(JoinPoint joinPoint, Guitar guitar) {
        var signature = (MethodSignature) joinPoint.getSignature();

        LOGGER.info("> Executing: {} from {} with guitar", signature.getName(), signature.getDeclaringTypeName(), guitar.getBrand());
    }
}
