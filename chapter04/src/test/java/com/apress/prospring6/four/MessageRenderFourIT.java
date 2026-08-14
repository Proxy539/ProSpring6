package com.apress.prospring6.four;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.apress.prospring6.four.impl.AllConfig;
import com.apress.prospring6.two.decoupled.MessageProvider;
import com.apress.prospring6.two.decoupled.MessageRenderer;

@Configuration
class TestConfig {

    // @Profile("test")
    // @Bean
    // public MessageProvider messageProvider() {
    // return new TestMessageProvider("Test message");
    // }
}

@ActiveProfiles("test")
@SpringJUnitConfig(classes = { AllConfig.class, TestConfig.class })
public class MessageRenderFourIT {

    @Autowired
    MessageRenderer messageRenderer;

    @Autowired
    MessageProvider messageProvider;

    @Test
    void testConfig() {
        // assertAll("messageTest",
        // () -> assertNotNull(messageRenderer),
        // () -> assertNotNull(messageProvider),
        // // () -> assertTrue(messageProvider instanceof TestMessageProvider),
        // // () -> assertEquals(messageProvider,
        // messageRenderer.getMessageProvider()));

        messageRenderer.render();

    }
}
