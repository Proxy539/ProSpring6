package com.apress.prospring6.three.scope;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class NonSingletonDemo {

    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext();
        ctx.register(Singer.class);
        ctx.refresh();

        var singer1 = ctx.getBean("nonSingleton", Singer.class);
        var singer2 = ctx.getBean("nonSingleton", Singer.class);

        System.out.println("Identity Equal?: " + (singer1 == singer2));
        System.out.println("Value Equal?: " + singer1.equals(singer2));

        System.out.println(singer1.toString());
        System.out.println(singer2.toString());
    }

}
