package com.apress.prospring6.five.boot;

import com.apress.prospring6.five.annotated.GrammyGuitarist;
import com.apress.prospring6.five.annotated.NewDocumentarist;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(basePackages = "com.apress.prospring6.five.annotated",
        useDefaultFilters = false,
        includeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = {GrammyGuitarist.class, NewDocumentarist.class}))
public class BootAopConfig {
}
