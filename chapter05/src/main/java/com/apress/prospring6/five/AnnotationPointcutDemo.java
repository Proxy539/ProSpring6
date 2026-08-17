package com.apress.prospring6.five;

import com.apress.prospring6.five.common.AdviceRequired;
import com.apress.prospring6.five.common.AnnotatedGuitarist;
import com.apress.prospring6.five.common.Guitar;
import com.apress.prospring6.five.pointcut.SimpleAroundAdvice;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;

public class AnnotationPointcutDemo {
    public static void main(String[] args) {
        var johnMayer = new AnnotatedGuitarist();
        var pc = AnnotationMatchingPointcut.forMethodAnnotation(AdviceRequired.class);

        var advisor = new DefaultPointcutAdvisor(pc, new SimpleAroundAdvice());
        ProxyFactory pf = new ProxyFactory();
        pf.setTarget(johnMayer);
        pf.addAdvisor(advisor);

        AnnotatedGuitarist proxy = (AnnotatedGuitarist) pf.getProxy();
        proxy.sing(new Guitar());
    }
}
