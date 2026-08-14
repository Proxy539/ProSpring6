package com.apress.prospring6.four;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.apress.prospring6.two.decoupled.MessageProvider;

public class MessageRendererOneIT {

    @Test
    public void testConfig() {
        // var ctx = new AnnotationConfigApplicationContext(RendererConfig.class,
        // ProviderConfig.class);
        //
        // var messageProvider = ctx.getBean(MessageProvider.class);
        // var messageRenderer = ctx.getBean(MessageRenderer.class);
        //
        // Assertions.assertAll("messageTest",
        // () -> assertNotNull(messageRenderer),
        // () -> assertNotNull(messageProvider),
        // () -> assertEquals(messageProvider, messageRenderer.getMessageProvider()));
        //
        // messageRenderer.render();
    }

}
