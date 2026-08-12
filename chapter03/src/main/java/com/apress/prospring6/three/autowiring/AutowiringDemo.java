package com.apress.prospring6.three.autowiring;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

public class AutowiringDemo {

    public static void main(String[] args) {

        var ctx = new AnnotationConfigApplicationContext(AutowiringCfg.class);
        var anotherTarget = ctx.getBean(AnotherTarget.class);

        System.out.println("anotherTarget: Created anotherTarget? " + (anotherTarget != null));
        System.out.println("anotherTarget: Injected bar? " + (anotherTarget.bar != null));
        System.out.println(
                "anotherTarget: Injected fooOne? " + (anotherTarget.fooOne != null ? anotherTarget.fooOne.id : ""));
        System.out.println("anotherTarget: Injected fooTwo? " + (anotherTarget != null ? anotherTarget.fooTwo.id : ""));

    }

}

@Configuration
@ComponentScan
class AutowiringCfg {

    @Bean
    public Foo anotherFoo() {
        return new Foo();
    }

}

@Component
@Lazy
class Target {
    Foo fooOne;
    Foo fooTwo;
    Bar bar;

    @Autowired
    public Target(@Qualifier("foo") Foo foo) {
        this.fooOne = foo;
        System.out.println("--> Target(foo) called");
    }

    public Target(@Qualifier("foo") Foo foo, Bar bar) {
        this.fooOne = foo;
        this.bar = bar;

        System.out.println("--> Target(Foo, Bar) called");
    }

}

@Component
class Foo {
    String id = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
}

@Component
class Bar {
}

@Component
@Lazy
class AnotherTarget {

    Foo fooOne;
    Foo fooTwo;
    Bar bar;

    @Autowired
    public void setFooOne(@Qualifier("foo") Foo fooOne) {
        System.out.println("--> AnotherTarget#setFooOne(Foo) called");
        this.fooOne = fooOne;
    }

    @Autowired
    public void setFooTwo(@Qualifier("anotherFoo") Foo fooTwo) {
        System.out.println("--> AnotherTarget#setFooTwo(Foo) called");
        this.fooTwo = fooTwo;
    }

    @Autowired
    public void setBar(Bar bar) {
        System.out.println("--> AnotherTarget#setBar(Bar) called");
        this.bar = bar;
    }

}

@Component
@Lazy
class FieldTarget {

    @Autowired
    @Qualifier("foo")
    Foo fooOne;
    @Autowired
    @Qualifier("anotherFoo")
    Foo fooTwo;
    @Autowired
    Bar bar;
}
