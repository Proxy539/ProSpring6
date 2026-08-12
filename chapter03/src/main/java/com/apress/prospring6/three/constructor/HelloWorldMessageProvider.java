package com.apress.prospring6.three.constructor;

import org.springframework.stereotype.Component;

import com.apress.prospring6.two.decoupled.MessageProvider;

//simple bean without dependencies
@Component("provider")
public class HelloWorldMessageProvider implements MessageProvider {

    @Override
    public String getMessage() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMessage'");
    }
    // some code omitted
}
