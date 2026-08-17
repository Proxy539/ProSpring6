package com.apress.prospring6.five.pointcut;

import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.support.DynamicMethodMatcherPointcut;

import java.lang.reflect.Method;

public class SimpleDynamicPointcut extends DynamicMethodMatcherPointcut {

    private static final Logger logger = LoggerFactory.getLogger(SimpleDynamicPointcut.class);

    @Override
    public ClassFilter getClassFilter() {
        return cls -> cls == GoodGuitarist.class;
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return "sing".equals(method.getName());
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass, @Nullable Object... args) {
        logger.debug("Dynamic check for " + method.getName());

        if (args.length == 0) {
            return false;
        }

        var key = (String) args[0];

        return key.equalsIgnoreCase("C");
    }
}
