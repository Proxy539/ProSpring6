package com.apress.prospring6.five.annotated;

import com.apress.prospring6.five.common.Dancer;
import com.apress.prospring6.five.common.Performer;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareParents;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AnnotatedIntroduction {

    @DeclareParents(value = "com.apress.prospring6.five.common.Singer+", defaultImpl = Dancer.class)
    public static Performer performer;
}
