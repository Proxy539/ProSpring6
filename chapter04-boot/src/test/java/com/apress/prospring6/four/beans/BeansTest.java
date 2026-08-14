package com.apress.prospring6.four.beans;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import com.apress.prospring6.two.decoupled.MessageProvider;
import com.apress.prospring6.two.decoupled.MessageRenderer;

@SpringBootTest
public class BeansTest {

    @Autowired
    ApplicationContext context;

    @Autowired
    MessageRenderer MessageRenderer;

    @Autowired
    MessageProvider messageProvider;

    @Test
    void contetLoaded() {
        assertNotNull(context);
    }

    @Test
    void rendererTest() {
        assertAll("messageTest",
                () -> assertNotNull(MessageRenderer),
                () -> assertNotNull(messageProvider),
                () -> assertEquals(messageProvider, MessageRenderer.getMessageProvider()));

        MessageRenderer.render();
    }
}
