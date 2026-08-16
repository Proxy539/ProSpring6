package com.apress.prospring6.five.pointcut;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GoodGuitarist implements Singer {

    private static Logger logger = LoggerFactory.getLogger(GoodGuitarist.class);

    @Override
    public void sing() {
        logger.info("Head on your heart, arms around me");
    }
}
