package com.apress.prospring6.five.performance;

public class DefaultSimpleBean implements SimpleBean {

    @Override
    public void advised() {
        System.currentTimeMillis();
    }

    @Override
    public void unadvised() {
        System.currentTimeMillis();
    }
}
