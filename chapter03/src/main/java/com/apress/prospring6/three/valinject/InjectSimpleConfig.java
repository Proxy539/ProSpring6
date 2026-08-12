package com.apress.prospring6.three.valinject;

import org.springframework.stereotype.Component;

@Component("injectSimpleConfig")
public class InjectSimpleConfig {

    private String name = "John Mayer";
    private int age = 40;
    private float height = 1.92f;
    private boolean developer = false;
    private Long ageInSeconds = 1_241_401_112L;

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public float getHeight() {
        return height;
    }

    public boolean isDeveloper() {
        return developer;
    }

    public Long getAgeInSeconds() {
        return ageInSeconds;
    }

}
