package com.apress.prospgin6.four.beans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import com.apress.prospring6.two.decoupled.MessageProvider;
import com.apress.prospring6.two.decoupled.MessageRenderer;

@Component("messageRenderer")
class StandardOutMessageRenderer implements MessageRenderer {

    private static Logger logger = LoggerFactory.getLogger(StandardOutMessageRenderer.class);

    private MessageProvider messageProvider;

    @Override
    public void render() {
        logger.info(messageProvider.getMessage());
    }

    @Autowired
    @Override
    public void setMessageProvider(MessageProvider provider) {
        this.messageProvider = provider;
    }

    @Override
    public MessageProvider getMessageProvider() {
        return messageProvider;
    }

}

@Component
class ConfigurableMessageProvider implements MessageProvider {
    private String message;

    public ConfigurableMessageProvider(@Value("Configurable message") String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

@SpringBootApplication
public class WithBeansApplication {
    private static Logger logger = LoggerFactory.getLogger(WithBeansApplication.class);

    public static void main(String[] args) {
        var ctx = SpringApplication.run(WithBeansApplication.class, args);

        MessageRenderer mr = ctx.getBean("messageRenderer", MessageRenderer.class);

        mr.render();
    }
}
