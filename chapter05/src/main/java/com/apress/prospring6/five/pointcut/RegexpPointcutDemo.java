package com.apress.prospring6.five.pointcut;

import com.apress.prospring6.five.common.Guitarist;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.JdkRegexpMethodPointcut;

public class RegexpPointcutDemo {
    public static void main(String[] args) {
        Guitarist johnMayer = new Guitarist();

        var pc = new JdkRegexpMethodPointcut();
        pc.setPattern(".*sing.*");

        Advisor advisor = new DefaultPointcutAdvisor(pc, new SimpleAroundAdvice());
        ProxyFactory pf = new ProxyFactory();
        pf.setTarget(johnMayer);
        pf.addAdvisor(advisor);


        Guitarist proxy = (Guitarist) pf.getProxy();
        proxy.sing();
        proxy.sing2();
        proxy.rest();
    }
}
