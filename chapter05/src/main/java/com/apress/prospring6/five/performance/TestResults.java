package com.apress.prospring6.five.performance;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestResults {

    private static Logger LOGGER = LoggerFactory.getLogger(TestResults.class);

    long advisedMethodTime;
    long unadvisedMethodTime;
    long equalsTime;
    long hashCodeTime;
    long proxyTargetTime;

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("advised", advisedMethodTime)
                .append("unadvised", unadvisedMethodTime)
                .append("equals", equalsTime)
                .append("hashCode", hashCodeTime)
                .append("getProxyTargetClass", proxyTargetTime)
                .toString();
    }
}
