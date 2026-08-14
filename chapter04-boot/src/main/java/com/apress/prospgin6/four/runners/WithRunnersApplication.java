package com.apress.prospgin6.four.runners;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.apress.prospring6.two.decoupled.MessageProvider;
import com.apress.prospring6.two.decoupled.MessageRenderer;

@Order(2)
@Component("messageRenderer")
class StandardOutMessageRenderer implements MessageRenderer, CommandLineRunner {
    // other methods omitted

    @Override
    public void run(String... args) throws Exception {
        render();
    }

    @Override
    public void render() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'render'");
    }

    @Override
    public void setMessageProvider(MessageProvider provider) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setMessageProvider'");
    }

    @Override
    public MessageProvider getMessageProvider() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMessageProvider'");
    }
}

@Order(1)
@Component
class ConfigurableMessageProvider implements MessageProvider, CommandLineRunner {

    private String message;

    public ConfigurableMessageProvider(@Value("Configurable message") String message) {
        this.message = message;
    }

    // getter omitted

    @Override
    public void run(String... args) throws Exception {
        if (args.length >= 1) {
            message = args[0];
        }
    }

    @Override
    public String getMessage() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMessage'");
    }
}

@SpringBootApplication
public class WithRunnersApplication {

    public static void main(String[] args) {
        SpringApplication.run(WithRunnersApplication.class, args);
    }
}
