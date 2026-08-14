package com.apress.prospring6.four;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;

public class EnvironmentTest {

    private static Logger logger = LoggerFactory.getLogger(EnvironmentTest.class);

    public static void main(String[] args) {
        var ctx = new GenericApplicationContext();

        ConfigurableEnvironment env = ctx.getEnvironment();
        MutablePropertySources propertySources = env.getPropertySources();

        Map<String, Object> appMap = new HashMap<>();
        appMap.put("user.home", "CUSTOM_USER_HOME");

        propertySources.addFirst(new MapPropertySource("prospring6_MAP", appMap));

        logger.info("-- Env Variables from java.lang.System --");
        logger.info("user.home: " + System.getProperty("user.home"));
        logger.info("JAVA_HOME: " + System.getenv("JAVA_HOME"));

        logger.info("-- Env Variables from ConfigurableEnvrionmenrt --");
        logger.info("usesr.home: " + env.getProperty("user.home"));
        logger.info("JAVA_HOME: " + env.getProperty("JAVA_HOME"));

        ctx.close();
    }

}
