package com.apress.prospring6.five.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnnotatedGuitarist implements Singer {

    private static Logger LOGGER = LoggerFactory.getLogger(AnnotatedGuitarist.class);

    @Override
    public void sing() {

    }

    @AdviceRequired
    public void sing(Guitar guitar) {
        LOGGER.info("play: " + guitar.play());
    }

    @Override
    public void rest() {

    }
}
