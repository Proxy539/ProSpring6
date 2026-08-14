package com.apress.prospring6.four;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.apress.prospring6.two.decoupled.MessageProvider;
import com.apress.prospring6.two.decoupled.MessageRenderer;

// @ExtendWith(SpringExtension.class)
// @ContextConfiguration(classes = { RendererConfig.class, ProviderConfig.class })
public class MessageRendererThreeIT {

    @Autowired
    MessageRenderer messageRenderer;

    @Autowired
    MessageProvider messageProvider;

    @Test
    public void testProvider() {
        assertNotNull(messageProvider);
    }

    @Test
    public void testRenderer() {
        assertAll("messageTest",
                () -> assertNotNull(messageRenderer),
                () -> assertNotNull(messageRenderer.getMessageProvider()));

        messageRenderer.render();
    }

}
