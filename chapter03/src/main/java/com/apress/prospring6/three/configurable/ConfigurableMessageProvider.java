package com.apress.prospring6.three.configurable;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.apress.prospring6.two.decoupled.MessageProvider;

@Component("provider")
public class ConfigurableMessageProvider implements MessageProvider {

    private String message;

    public ConfigurableMessageProvider(@Value("Configurable message") String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
