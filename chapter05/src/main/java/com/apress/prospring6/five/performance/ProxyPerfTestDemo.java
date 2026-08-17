package com.apress.prospring6.five.performance;

import com.apress.prospring6.five.NameMatchMethodPointcutAdvisorDemo;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.Advisor;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.config.SimpleBeanFactoryAwareAspectInstanceFactory;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.aop.support.NameMatchMethodPointcutAdvisor;

import java.awt.*;
import java.lang.reflect.Method;

class NoOpBeforeAdvise implements MethodBeforeAdvice {
    @Override
    public void before(Method method, @Nullable Object[] args, @Nullable Object target) throws Throwable {
        //no op
    }
}

public class ProxyPerfTestDemo {

    private static Logger LOGGER = LoggerFactory.getLogger(ProxyPerfTestDemo.class);

    public static void main(String[] args) {
        SimpleBean target = new DefaultSimpleBean();

        NameMatchMethodPointcutAdvisor advisor = new NameMatchMethodPointcutAdvisor(new NoOpBeforeAdvise());
        advisor.setMappedNames("advised");

        LOGGER.info("Starting tests ... ");
        runCglibTests(advisor, target);
        runCglibFrozenTests(advisor, target);
        runJdkTests(advisor, target);
    }

    private static void runCglibTests(Advisor advisor, SimpleBean target) {
        ProxyFactory pf = new ProxyFactory();
        pf.setProxyTargetClass(true);
        pf.setTarget(target);
        pf.addAdvisor(advisor);

        SimpleBean proxy = (SimpleBean) pf.getProxy();
        var testResults = test(proxy);
        LOGGER.info("--- CGLIB (Standard) Test results ---\n {}", testResults);
    }

    private static void runCglibFrozenTests(Advisor advisor, SimpleBean target) {
        ProxyFactory pf = new ProxyFactory();
        pf.setProxyTargetClass(true);
        pf.setTarget(target);
        pf.addAdvisor(advisor);
        pf.setFrozen(true);
        SimpleBean proxy = (SimpleBean) pf.getProxy();
        var testResult = test(proxy);
        LOGGER.info("--- GLIB (Frozen) Test results ---\n {}", testResult);
    }

    private static void runJdkTests(Advisor advisor, SimpleBean target) {
        ProxyFactory pf = new ProxyFactory();
        pf.setTarget(target);
        pf.addAdvisor(advisor);
        pf.setInterfaces(SimpleBean.class);
        SimpleBean proxy = (SimpleBean) pf.getProxy();
        var testsResults = test(proxy);
        LOGGER.info("--- JDK Test results ---\n {}", testsResults);
    }

    private static TestResults test(SimpleBean bean) {
        TestResults testResults = new TestResults();
        long before = System.currentTimeMillis();
        for (int x = 0; x < 500000; x++) {
            bean.advised();
        }

        long after = System.currentTimeMillis();
        testResults.advisedMethodTime = after - before;

        //-----
        before = System.currentTimeMillis();
        for (int x = 0; x < 500000; x++) {
            bean.unadvised();
        }
        after = System.currentTimeMillis();
        testResults.unadvisedMethodTime = after - before;

        //-----
        before = System.currentTimeMillis();
        for (int x = 0; x < 500000; x++) {
            bean.equals(bean);
        }

        after = System.currentTimeMillis();
        testResults.equalsTime = after - before;

        //----

        before = System.currentTimeMillis();
        for (int x = 0; x < 500000; x++) {
            bean.hashCode();
        }

        after = System.currentTimeMillis();
        testResults.hashCodeTime = after - before;

        //---

        Advised advised = (Advised) bean;
        before = System.currentTimeMillis();
        for (int x = 0; x < 500000; x++) {
            advised.getTargetClass();
        }

        after = System.currentTimeMillis();
        testResults.proxyTargetTime = after - before;
        return testResults;
    }

}
