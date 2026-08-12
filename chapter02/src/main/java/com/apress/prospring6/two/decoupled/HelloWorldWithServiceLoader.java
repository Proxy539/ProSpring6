package com.apress.prospring6.two.decoupled;

import java.util.ServiceLoader;

public class HelloWorldWithServiceLoader {

    public static void main(String[] args) {
        ServiceLoader<MessageRenderer> slr = ServiceLoader.load(MessageRenderer.class);
        ServiceLoader<MessageProvider> slp = ServiceLoader.load(MessageProvider.class);

        MessageRenderer mr = slr.findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Service of type 'MessageRenderer' was not found!"));

        MessageProvider mp = slp.findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Service of type 'MessageProvider' was not found!"));

        mr.setMessageProvider(mp);
        mr.render();
    }

}
