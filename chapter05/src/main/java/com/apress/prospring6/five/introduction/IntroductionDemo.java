package com.apress.prospring6.five.introduction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.IntroductionAdvisor;
import org.springframework.aop.framework.ProxyFactory;

public class IntroductionDemo {

    private static Logger LOGGER = LoggerFactory.getLogger(IntroductionDemo.class);

    public static void main(String[] args) {
        Contact target = new Contact();
        target.setName("John Mayer");

        IntroductionAdvisor advisor = new IsModifiedAdvisor();
        ProxyFactory pf = new ProxyFactory();
        pf.setTarget(target);
        pf.addAdvisor(advisor);
        pf.setOptimize(true);


        Contact proxy = (Contact) pf.getProxy();
        IsModified proxyInterface = (IsModified) proxy;

        LOGGER.info("Is Contact? => {}", proxy instanceof Contact);
        LOGGER.info("Is IsModified? => {}", proxy instanceof IsModified);
        LOGGER.info("Has been modified? => {}", proxyInterface.isModified());

        proxy.setName("John Mayer");
        LOGGER.info("Has been modified? => {}", proxyInterface.isModified());

        proxy.setName("Ben Barnes");
        LOGGER.info("Has been modified? => {}", proxyInterface.isModified());
    }
}
