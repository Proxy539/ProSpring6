package com.apress.prospring6.seven.base;

import com.apress.prospring6.seven.base.config.HibernateConfig;
import com.apress.prospring6.seven.base.dao.SingerDao;
import com.apress.prospring6.seven.base.entities.Singer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class HibernateDemoV2 {
    private static final Logger LOGGER = LoggerFactory.getLogger(HibernateDemoV2.class);

    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext(HibernateConfig.class);
        var singerDao = ctx.getBean(SingerDao.class);
        var singer = singerDao.findById(2L);

        listSingersWithAlbum(singerDao.findAllWithAlbum());
        ctx.close();
    }

    private static void listSingersWithAlbum(List<Singer> singers) {
        LOGGER.info("--- Listing singers with instruments:");
        singers.forEach(s -> {
            LOGGER.info(s.toString());
            if (s.getAlbums() != null) {
                s.getAlbums().forEach(a -> LOGGER.info("\t" + a.toString()));
            }
            if (s.getInstruments() != null) {
                s.getInstruments().forEach(i -> LOGGER.info("\tInstrument: " + i.getInstrumentId()));
            }
        });
    }
}
