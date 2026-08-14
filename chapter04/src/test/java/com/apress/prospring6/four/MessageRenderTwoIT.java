package com.apress.prospring6.four;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.apress.prospring6.two.decoupled.MessageProvider;

public class MessageRenderTwoIT {

    // public static ApplicationContext ctx;
    //
    // @BeforeAll
    // static void setUp() {
    // ctx = new AnnotationConfigApplicationContext(RendererConfig.class,
    // ProviderConfig.class);
    // }
    //
    // @Test
    // public void testProvider() {
    // var messageProvider = ctx.getBean(MessageProvider.class);
    // assertNotNull(messageProvider);
    // }
    //
    // @Test
    // public void testRenderer() {
    // var messageRenderer = ctx.getBean(MessageRenderer.class);
    // assertAll("messageTest",
    // () -> assertNotNull(messageRenderer),
    // () -> assertNotNull(messageRenderer.getMessageProvider()));
    //
    // messageRenderer.render();
    // }

}
