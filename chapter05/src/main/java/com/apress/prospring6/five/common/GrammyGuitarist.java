package com.apress.prospring6.five.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GrammyGuitarist implements Singer {

    private static Logger LOGGER = LoggerFactory.getLogger(GrammyGuitarist.class);

    @Override
    public void sing() {
        LOGGER.info("sing: Gravity is working against me\nAnd Gravity wants to bring me down");
    }

    public void sing(Guitar guitar) {
        LOGGER.info("play: " + guitar.play());
    }

    public void talk() {
        LOGGER.info("talk");
    }

}


