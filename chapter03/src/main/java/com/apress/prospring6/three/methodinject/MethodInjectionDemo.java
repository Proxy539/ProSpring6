package com.apress.prospring6.three.methodinject;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.StopWatch;

public class MethodInjectionDemo {

    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext(LookupConfig.class);

        var abstractLockOpener = ctx.getBean("abstractLockOpener", LockOpener.class);
        var standardLockOpener = ctx.getBean("standardLockOpener", LockOpener.class);

        displayInfo("abstractLockOpener", abstractLockOpener);
        displayInfo("stanradLockOpener", standardLockOpener);
    }

    public static void displayInfo(String beanName, LockOpener lockOpener) {
        KeyHelper keyHelperOne = lockOpener.getMyKeyOpener();
        KeyHelper keyHelperTwo = lockOpener.getMyKeyOpener();

        System.out.println("[" + beanName + "]: KeyHekper Instances the Same? " + (keyHelperOne == keyHelperTwo));

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        for (int x = 0; x < 100_000; x++) {
            KeyHelper keyHelper = lockOpener.getMyKeyOpener();
            keyHelper.open();
        }

        stopWatch.stop();

        System.out.println("100000 gets took " + stopWatch.getTotalTimeMillis() + " ms");
    }

}
