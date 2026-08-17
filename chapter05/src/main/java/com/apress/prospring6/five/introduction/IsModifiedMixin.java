package com.apress.prospring6.five.introduction;

import org.aopalliance.intercept.MethodInvocation;
import org.jspecify.annotations.Nullable;
import org.springframework.aop.support.DelegatingIntroductionInterceptor;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class IsModifiedMixin extends DelegatingIntroductionInterceptor implements IsModified {

    private boolean isModified = false;
    private final Map<Method, Method> methodCache = new HashMap<>();
    private final Predicate<MethodInvocation> isSetter = invocation ->
            invocation.getMethod().getName().startsWith("set") && (invocation.getArguments().length == 1);

    @Override
    public boolean isModified() {
       return isModified;
    }

    @Override
    public @Nullable Object invoke(MethodInvocation mi) throws Throwable {
        if (!isModified) {
            if (isSetter.test(mi)) {
                Method getter = getGetter(mi.getMethod());

                if (getter != null) {
                    Object newVal = mi.getArguments()[0];
                    Object oldVal = getter.invoke(mi.getThis(), null);
                    if (newVal == null && oldVal == null) {
                        isModified = false;
                    } else if ((newVal == null && oldVal != null) || (newVal != null && oldVal == null)) {
                        isModified = true;
                    } else {
                        isModified = !newVal.equals(oldVal);
                    }
                }
            }
        }

        return super.invoke(mi);
    }

    private Method getGetter(Method setter) {
        Method getter = methodCache.get(setter);
        if (getter != null) {
            return getter;
        }

        String getterName = setter.getName().replaceFirst("set", "get");

        try {
            getter = setter.getDeclaringClass().getMethod(getterName, null);
            synchronized (methodCache) {
                methodCache.put(setter, getter);
            }
            return getter;
        } catch (NoSuchMethodException ex) {
            return null;
        }
    }
}
