package com.apress.prospring6.five.boot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class AopTest {

    @Autowired
    ApplicationContext context;

    @Autowired
    GrammyGuitarist guitarist;

    @Test
    void contextLoads() {
        assertNotNull(context);
    }

    @Test
    void aspectApplied() {
        assertNotNull(guitarist);
        guitarist.sing();
    }
}
