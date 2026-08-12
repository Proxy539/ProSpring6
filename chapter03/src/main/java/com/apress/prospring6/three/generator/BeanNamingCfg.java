package com.apress.prospring6.three.generator;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(nameGenerator = SimpleBeanNameGenerator.class)
public class BeanNamingCfg {

}
