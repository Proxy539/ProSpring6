package com.apress.prospring6.five;

import com.apress.prospring6.five.common.GrammyGuitarist;
import com.apress.prospring6.five.common.Guitar;
import com.apress.prospring6.five.pointcut.SimpleAroundAdvice;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.NameMatchMethodPointcutAdvisor;

public class NameMatchMethodPointcutAdvisorDemo {
    public static void main(String[] args) {
        GrammyGuitarist johnMayer = new GrammyGuitarist();

        NameMatchMethodPointcutAdvisor advisor =
                new NameMatchMethodPointcutAdvisor(new SimpleAroundAdvice());

        advisor.setMappedNames("sing", "rest");


        ProxyFactory pf = new ProxyFactory();
        pf.setTarget(johnMayer);
        pf.addAdvisor(advisor);

        GrammyGuitarist proxy = (GrammyGuitarist) pf.getProxy();
        proxy.sing();
        proxy.sing(new Guitar());
        proxy.talk();
    }
}
