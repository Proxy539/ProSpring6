package com.apress.prospring6.five.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface Singer {

    Logger logger = LoggerFactory.getLogger(Singer.class);

    void sing();

    default void sing(String key) {
        logger.info("Singing in the key of {}", key);
    }

}
