package com.apress.prospring6.five.pointcut;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GreatGuitarist implements Singer {

    private static Logger logger = LoggerFactory.getLogger(GreatGuitarist.class);

    @Override
    public void sing() {
        logger.info("You've got my soul in your hand");
    }
}
