package com.apress.prospring6.three;

public class Singleton {

    private static final Singleton instance;

    static {
        instance = new Singleton();
    }

    public static Singleton getInstance() {
        return instance;
    }
}
