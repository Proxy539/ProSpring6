package com.apress.prospring6.four.impl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.apress.prospring6.two.decoupled.MessageProvider;
import com.apress.prospring6.two.decoupled.MessageRenderer;
import com.apress.prospring6.two.decoupled.StandardOutMessageRenderer;

@Configuration
public class AllConfig {

    // @Profile("dev")
    // @Bean
    // public MessageProvider messageProvider() {
    // return new ConfigurableMessageProvider("Test Sample");
    // }
    //
    // @Bean
    // public MessageRenderer messageRenderer() {
    // MessageRenderer messageRenderer = new StandardOutMessageRenderer();
    // messageRenderer.setMessageProvider(messageProvider());
    // return messageRenderer;
    // }
}
