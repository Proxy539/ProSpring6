package com.apress.prospring6.five;

import com.apress.prospring6.five.advance.SimpleBeforeAdvance;
import com.apress.prospring6.five.common.GrammyGuitarist;
import com.apress.prospring6.five.common.Guitar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.Advisor;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcher;

import java.lang.reflect.Method;

public class ComposablePointcutDemo {

    private static Logger LOGGER = LoggerFactory.getLogger(ComposablePointcutDemo.class);

    public static void main(String[] args) {
        GrammyGuitarist johnMayer = new GrammyGuitarist();
        ComposablePointcut pc = new ComposablePointcut(ClassFilter.TRUE, new SingMethodMatcher());

        LOGGER.info("Test 1 >> ");
        GrammyGuitarist proxy = getProxy(pc, johnMayer);
        testInvoke(proxy);

        LOGGER.info("Test 2 >> ");
        pc.union(new TalkMethodMatcher());
        proxy = getProxy(pc, johnMayer);
        testInvoke(proxy);

        LOGGER.info("Test 3 >> ");
        pc.intersection(new RestMethodMatcher());
        proxy = getProxy(pc, johnMayer);
        testInvoke(proxy);
    }

    private static GrammyGuitarist getProxy(ComposablePointcut pc, GrammyGuitarist target) {
        Advisor advisor = new DefaultPointcutAdvisor(pc, new SimpleBeforeAdvance());

        ProxyFactory pf = new ProxyFactory();
        pf.setTarget(target);
        pf.addAdvisor(advisor);


        return (GrammyGuitarist) pf.getProxy();
    }

    private static void testInvoke(GrammyGuitarist proxy) {
        proxy.sing();
        proxy.sing(new Guitar());
        proxy.talk();
    }

}

class SingMethodMatcher extends StaticMethodMatcher {
    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return (method.getName().startsWith("si"));
    }
}

class TalkMethodMatcher extends StaticMethodMatcher {
    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return "talk".equals(method.getName());
    }
}

class RestMethodMatcher extends StaticMethodMatcher {
    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return (method.getName().endsWith("st"));
    }
}
