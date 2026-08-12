package com.apress.prospring6.three.annotation;

import org.springframework.core.annotation.AliasFor;

import com.apress.prospring6.three.alias.Award;

@Award
public @interface Trophy {
    @AliasFor(annotation = Award.class, attribute = "value")
    String[] name() default {};

}
