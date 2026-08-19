package com.apress.prospring6.six.repo;

import com.apress.prospring6.six.config.SpringDatasourceCfg;
import com.apress.prospring6.six.records.Singer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class RepoDemo {

    private static Logger LOGGER = LoggerFactory.getLogger(RepoDemo.class);

    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext(SpringDatasourceCfg.class);
        var singeRepo = ctx.getBean("singerRepo", SingerRepo.class);

        LOGGER.info("-----------------------------");
        List<Singer> singers = singeRepo.findAll();
        singers.forEach(singer -> LOGGER.info(singer.toString()));

        LOGGER.info("------------------------------");
        String firstName = singeRepo.findFirstNameById(2L).orElse(null);
        LOGGER.info("Retrieved {}", firstName);
        ctx.close();
    }
}
