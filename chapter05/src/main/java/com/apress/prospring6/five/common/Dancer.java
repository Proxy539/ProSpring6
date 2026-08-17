package com.apress.prospring6.five.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Dancer implements Performer {

    private static Logger LOGGER = LoggerFactory.getLogger(Dancer.class);

    @Override
    public void perform() {
        LOGGER.info("Shake it to the left, shake it to the right!");
    }
}
