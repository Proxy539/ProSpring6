package com.apress.prospring6.five.boot;

import com.apress.prospring6.five.annotated.NewDocumentarist;
import com.apress.prospring6.five.common.GrammyGuitarist;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class AopTest {

    @Autowired
    NewDocumentarist documentarist;

    @Autowired
    GrammyGuitarist guitarist;

    @Test
    void testDocumentarist() {
        assertAll(
                () -> assertNotNull(documentarist.getGuitarist()),
                () -> assertNotNull(guitarist),
                () -> assertTrue(guitarist.getClass().getName().contains("SpringCGLIB"))
        );

        documentarist.execute();
    }
}
