package com.apress.prospring6.five.annotated;

import com.apress.prospring6.five.common.Guitar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("johnMayer")
public class GrammyGuitarist extends com.apress.prospring6.five.common.GrammyGuitarist {

    private static Logger LOGGER = LoggerFactory.getLogger(GrammyGuitarist.class);

    @Override
    public void sing() {
        LOGGER.info("sing: Gravity is working against me\nAnd gravity wants to bring me down");
    }

    public void sing(Guitar guitar) {
        LOGGER.info("play: " + guitar.play());
    }

    public void talk() {
        LOGGER.info("talk");
    }
}
