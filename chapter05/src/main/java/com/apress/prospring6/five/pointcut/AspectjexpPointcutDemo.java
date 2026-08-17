package com.apress.prospring6.five.pointcut;

import com.apress.prospring6.five.common.Guitarist;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

public class AspectjexpPointcutDemo {
    public static void main(String[] args) {
        Guitarist johnMayer = new Guitarist();

        var pc = new AspectJExpressionPointcut();
        pc.setExpression("execution(* sing*(..))");

        var advisor = new DefaultPointcutAdvisor(pc, new SimpleAroundAdvice());

        ProxyFactory pf = new ProxyFactory();
        pf.setTarget(johnMayer);
        pf.addAdvisor(advisor);

        Guitarist proxy = (Guitarist) pf.getProxy();
        proxy.sing();
        proxy.sing2();
        proxy.rest();
    }
}
