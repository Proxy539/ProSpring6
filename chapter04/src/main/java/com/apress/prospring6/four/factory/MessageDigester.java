package com.apress.prospring6.four.factory;

import java.security.MessageDigest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageDigester {

    private static Logger logger = LoggerFactory.getLogger(MessageDigester.class);

    private MessageDigest digest1;
    private MessageDigest digest2;

    public void setDigest1(MessageDigest digest1) {
        this.digest1 = digest1;
    }

    public void setDigest2(MessageDigest digest2) {
        this.digest2 = digest2;
    }

    public void digest(String msg) {
        logger.info("Using digest1");
        digest(msg, digest1);

        logger.info("Using digest2");
        digest(msg, digest2);
    }

    private void digest(String msg, MessageDigest digest) {
        logger.info("Using algorirthm: " + digest.getAlgorithm());
        digest.reset();
        byte[] bytes = msg.getBytes();
        byte[] out = digest.digest(bytes);

        // we are printing the actual byte values
        logger.info("Original Message: {}", bytes);
        logger.info("Encrypted Message: {}", out);

    }

}
