package com.apress.prospring6.five.advice;

import com.apress.prospring6.five.annotated.RejectedInstrumentException;
import com.apress.prospring6.five.common.Guitar;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Aspect
public class AfterThrowingAdviceV2 {

    private static Logger LOGGER = LoggerFactory.getLogger(AfterThrowingAdviceV2.class);

    @Pointcut("execution(* com.apress.prospring6.five..PretentiosGuitarist.sing*(com.apress.prospring6.five.common.Guitar)) && args(value)")
    public void singExecution(Guitar value) {

    }

    @AfterThrowing(value = "singExecution(guitar)", argNames = "joinPoint,guitar, ex", throwing = "ex")
    public void simpleAfterAdvice(JoinPoint joinPoint, Guitar guitar, IllegalArgumentException ex) {
        var signature = (MethodSignature) joinPoint.getSignature();
        LOGGER.info("> Executed: {} from {} with guitar {}", signature.getName(), signature.getDeclaringTypeName(), guitar.getBrand());
        if (ex.getMessage().contains("Unacceptable guitar!")) {
            throw new RejectedInstrumentException(ex.getMessage(), ex);
        }
    }
}
