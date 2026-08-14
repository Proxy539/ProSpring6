package com.apress.prospring6.four;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.apress.prospring6.two.decoupled.MessageProvider;
import com.apress.prospring6.two.decoupled.MessageRenderer;
import com.apress.prospring6.two.decoupled.StandardOutMessageRenderer;

public class MessageRendererTest {

    @Test
    void testStandardOutMessageRenderer() {
        MessageProvider mockProvider = Mockito.mock(MessageProvider.class);

        Mockito.when(mockProvider.getMessage()).thenReturn("test message");

        MessageRenderer messageRenderer = new StandardOutMessageRenderer();
        messageRenderer.setMessageProvider(mockProvider);

        messageRenderer.render();
        Mockito.verify(mockProvider, Mockito.times(1)).getMessage();
    }

}
