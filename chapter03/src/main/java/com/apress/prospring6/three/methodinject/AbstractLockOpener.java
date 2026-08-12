package com.apress.prospring6.three.methodinject;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;

@Component("abstractLockOpener")
public abstract class AbstractLockOpener implements LockOpener {

    @Lookup("keyHelper")
    @Override
    public abstract KeyHelper getMyKeyOpener();

    @Override
    public void openLock() {
        getMyKeyOpener().open();
    }

    @Override
    public String toString() {
        return "AbstractLockOpener []";
    }

}
