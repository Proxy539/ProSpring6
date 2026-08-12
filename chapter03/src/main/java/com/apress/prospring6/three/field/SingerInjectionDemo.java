package com.apress.prospring6.three.field;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SingerInjectionDemo {

    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext();
        ctx.register(Singer.class, Inspiration.class);
        ctx.refresh();

        Singer singerBean = ctx.getBean(Singer.class);
        singerBean.sign();
    }

}
