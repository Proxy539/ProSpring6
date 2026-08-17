package com.apress.prospring6.five.annotated;

import com.apress.prospring6.five.advice.AfterAdviceV1;
import com.apress.prospring6.five.advice.AfterThrowingAdviceV2;
import com.apress.prospring6.five.advice.BeforeAdviceV1;
import com.apress.prospring6.five.advice.BeforeAdviceV5;
import com.apress.prospring6.five.common.GrammyGuitarist;
import com.apress.prospring6.five.common.Guitar;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AnnotatedAdviceTest {

    private static Logger LOGGER = LoggerFactory.getLogger(AnnotatedAdviceTest.class);

    @Test
    void testBeforeAdviceV1() {
        var ctx = new AnnotationConfigApplicationContext();
        ctx.register(AspectJAopConfig.class, BeforeAdviceV1.class);
        ctx.refresh();
        assertTrue(Arrays.asList(ctx.getBeanDefinitionNames()).contains("beforeAdviceV1"));

        NewDocumentarist documentarist = ctx.getBean("documentarist", NewDocumentarist.class);
        documentarist.execute();
        ctx.close();
    }

    @Test
    void testAfterAdviceV1() {
        var ctx = new AnnotationConfigApplicationContext();
        ctx.register(AspectJAopConfig.class, AfterAdviceV1.class);
        ctx.refresh();
        assertTrue(Arrays.asList(ctx.getBeanDefinitionNames()).contains("afterAdviceV1"));

        var guitar = new Guitar();
        var guitarist = ctx.getBean("augustin", PretentiosGuitarist.class);
        guitarist.sing(guitar);
        LOGGER.info("--------------------------");
        guitar.setBrand("Musicman");

        assertThrows(IllegalArgumentException.class, () -> guitarist.sing(guitar), "Unacceptable guitar!");
        ctx.close();
    }

    @Test
    void testAfterThrowingAdviceV2() {
        var ctx = new AnnotationConfigApplicationContext();
        ctx.register(AspectJAopConfig.class, AfterThrowingAdviceV2.class);
        ctx.refresh();
        assertTrue(Arrays.asList(ctx.getBeanDefinitionNames()).contains("afterThrowingAdviceV2"));

        var guitar = new Guitar();
        var guitarist = ctx.getBean("agustin", PretentiosGuitarist.class);
        guitarist.sing(guitar);
        LOGGER.info("---------------");
        guitar.setBrand("Musicman");

        assertThrows(RejectedInstrumentException.class, () -> guitarist.sing(guitar), "Unacceptable guitar!");
        ctx.close();
    }

    @Test
    void testAfterThrowingAdviceV5() {
        var ctx = new AnnotationConfigApplicationContext();
        ctx.register(AspectJAopConfig.class, BeforeAdviceV5.class);
        ctx.refresh();
        assertTrue(Arrays.asList(ctx.getBeanDefinitionNames()).contains("beforeAdviceV5"));

        var johnMayer = ctx.getBean("johnMayer", GrammyGuitarist.class);
        johnMayer.sing(new Guitar());

        var pretentiousGuitarist = ctx.getBean("agustin", GrammyGuitarist.class);
        johnMayer.sing(new Guitar());

        ctx.close();
    }
}
