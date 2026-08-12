package com.apress.prospring6.two.annotated;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.apress.prospring6.two.decoupled.HelloWorldMessageProvider;
import com.apress.prospring6.two.decoupled.MessageProvider;
import com.apress.prospring6.two.decoupled.MessageRenderer;
import com.apress.prospring6.two.decoupled.StandardOutMessageRenderer;

@Configuration
public class HelloWorldConfiguration {

    @Bean // equivalent to <bean id = "provider" class=".."/>
    public MessageProvider provider() {
        return new HelloWorldMessageProvider();
    }

    @Bean // equivalent to <bean id="renderer" class=".."/>
    public MessageRenderer renderer() {
        MessageRenderer renderer = new StandardOutMessageRenderer();
        renderer.setMessageProvider(provider());
        return renderer;
    }

}
