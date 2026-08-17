package com.apress.prospring6.five;

import com.apress.prospring6.five.common.Documentarist;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ProxyFactoryBeanDemo {

    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext(AopConfig.class);

        Documentarist documentaristOne = ctx.getBean("documentaristOne", Documentarist.class);
        Documentarist documentaristTwo = ctx.getBean("documentaristTwo", Documentarist.class);

        System.out.println("Documentarist One >> ");
        documentaristOne.execute();

        System.out.println("Documentarist Two >> ");
        documentaristTwo.execute();
    }
}
