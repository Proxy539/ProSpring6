package com.apress.prospring6.three.setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.apress.prospring6.three.Container;
import com.apress.prospring6.three.MessageRenderer;
import com.apress.prospring6.two.decoupled.MessageProvider;

//simple bean requiring a dependency
@Component("renderer")
public class StandardOutMessageRenderer implements MessageRenderer {

    private MessageProvider messageProvider;

    @Autowired
    public void setMessageProvider(MessageProvider provider) {
        System.out.println(" ~~ Injecting dependency using setter ~~");
        this.messageProvider = provider;
    }

    @Override
    public void performLookup(Container container) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'performLookup'");
    }

    @Override
    public void render() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'render'");
    }

    // some code omitted

}
