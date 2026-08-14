package com.apress.prospring6.four.initmethod;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SingerConfiguration {

    @Bean
    public Singer singerOne() {
        Singer singer = new Singer();
        singer.setName("John Mayer");
        singer.setAge(43);
        return singer;
    }

    @Bean
    public Singer singerTwo() {
        Singer singer = new Singer();
        singer.setAge(42);
        return singer;
    }

    @Bean
    public Singer singerThree() {
        Singer singer = new Singer();
        singer.setName("John Butler");
        return singer;
    }

}
