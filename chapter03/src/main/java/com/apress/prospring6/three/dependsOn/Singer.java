package com.apress.prospring6.three.dependsOn;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

@Component("gopher")
class Guitar {
    public void sign() {
        System.out.println("Cm Eb Fm Ab Bb");
    }
}

@DependsOn("gopher")
@Component("johnMayer")
public class Singer implements ApplicationContextAware {

    private ApplicationContext ctx;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }

    private Guitar guitar;

    public Singer() {

    }

    public void sing() {
        guitar = ctx.getBean("gopher", Guitar.class);
        guitar.sign();
    }
}
