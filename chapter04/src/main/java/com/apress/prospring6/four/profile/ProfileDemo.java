package com.apress.prospring6.four.profile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.apress.prospring6.four.profile.highschool.HighSchoolConfig;
import com.apress.prospring6.four.profile.kindergarten.KindergartenConfig;

public class ProfileDemo {

    private static Logger logger = LoggerFactory.getLogger(ProfileDemo.class);

    public static void main(String[] args) {

        var ctx = new AnnotationConfigApplicationContext();

        ctx.register(HighSchoolConfig.class, KindergartenConfig.class);
        ctx.refresh();

        var foodProviderService = ctx.getBean(FoodProviderService.class);
        var lunchSet = foodProviderService.provideLunchSet();
        lunchSet.forEach(food -> logger.info("Food: {}", food.getName()));
        ctx.close();

    }

}
