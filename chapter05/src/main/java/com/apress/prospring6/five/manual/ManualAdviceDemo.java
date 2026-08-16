package com.apress.prospring6.five.manual;

import com.apress.prospring6.five.pointcut.Concert;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.util.StopWatch;

import java.lang.reflect.Method;

class SimpleBeforeAdvice implements MethodBeforeAdvice {

    private static Logger logger = LoggerFactory.getLogger(SimpleBeforeAdvice.class);


    @Override
    public void before(Method method, @Nullable Object[] args, @Nullable Object target) throws Throwable {
        logger.info("Before: set up concert hall.");
    }
}

class SimpleAfterAdvice implements AfterReturningAdvice {

    private static Logger logger = LoggerFactory.getLogger(SimpleAfterAdvice.class);

    @Override
    public void afterReturning(@Nullable Object returnValue, Method method, @Nullable Object[] args, @Nullable Object target) throws Throwable {
        logger.info("After: offer standing ovation.");
    }
}

class SimpleAroundAdvice implements MethodInterceptor {

    private static Logger logger = LoggerFactory.getLogger(com.apress.prospring6.five.pointcut.SimpleAroundAdvice.class);

    @Override
    public @Nullable Object invoke(MethodInvocation invocation) throws Throwable {
        logger.info("Around: starting timer");

        StopWatch sw = new StopWatch();
        sw.start(invocation.getMethod().getName());
        Object returnValue = invocation.proceed();
        sw.stop();

        logger.info("Around: concert duration = {}", sw.getTotalTimeMillis());
        return returnValue;
    }
}

public class ManualAdviceDemo {

    public static void main(String[] args) {
        Concert concert = new Concert();

        ProxyFactory pf = new ProxyFactory();
        pf.addAdvice(new SimpleBeforeAdvice());
        pf.addAdvice(new com.apress.prospring6.five.pointcut.SimpleAroundAdvice());
        pf.addAdvice(new SimpleAfterAdvice());
        pf.setTarget(concert);
        
        Performance proxy = (Performance) pf.getProxy();

        proxy.execute();
    }
}
