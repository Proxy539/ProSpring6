package com.apress.prospring6.three.collectioninject;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CollectionInjectionDemo {

    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext();
        ctx.register(CollectionConfig.class, CollectingBean.class);
        ctx.refresh();

        var collectingBean = ctx.getBean(CollectingBean.class);
        collectingBean.printCollections();
    }
}
