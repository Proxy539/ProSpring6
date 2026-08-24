package com.apress.prospring6.eleven.domain;

import org.springframework.core.convert.converter.Converter;

import java.net.URL;

public record SimpleBlogger(String fullName, URL personalSite) {

    public static class BloggerToSimpleBloggerConverter implements Converter<Blogger, SimpleBlogger> {
        @Override
        public SimpleBlogger convert(Blogger source) {
            return new SimpleBlogger(source.firstName() + " " + source.lastName(), source.personalSite());
        }
    }
}
