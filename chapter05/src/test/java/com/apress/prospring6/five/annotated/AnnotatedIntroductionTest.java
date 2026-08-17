package com.apress.prospring6.five.annotated;

import com.apress.prospring6.five.common.Guitar;
import com.apress.prospring6.five.common.Performer;
import com.apress.prospring6.five.common.Singer;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AnnotatedIntroductionTest {
    private static Logger LOGGER = LoggerFactory.getLogger(AnnotatedIntroductionTest.class);

    @Test
    void testAnnotatedIntroduction() {
        var ctx = new AnnotationConfigApplicationContext();
        ctx.register(AspectJAopConfig.class, AnnotatedIntroduction.class);
        ctx.refresh();

        assertTrue(Arrays.asList(ctx.getBeanDefinitionNames()).contains("annotatedIntroduction"));

        var guitar = new Guitar();
        var guitarist = ctx.getBean("agustin", PretentiosGuitarist.class);

        assertTrue(guitarist instanceof Singer);
        guitarist.sing(guitar);

        LOGGER.info("Proxy type: {}", guitar.getClass().getName());

        assertTrue(guitarist instanceof Performer);
        Performer performer = (Performer) guitarist;
        performer.perform();

        ctx.close();
    }
}
