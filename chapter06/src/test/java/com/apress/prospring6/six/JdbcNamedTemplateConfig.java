package com.apress.prospring6.six;

import com.apress.prospring6.six.rowmapper.SingerDao;
import com.apress.prospring6.six.template.SpringJdbcTemplateCfg;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JdbcNamedTemplateConfig {

    private static Logger LOGGER = LoggerFactory.getLogger(JdbcNamedTemplateConfig.class);

    @Test
    public void testSpringJdbc() {
        var ctx = new AnnotationConfigApplicationContext(SpringJdbcTemplateCfg.class);

//        var singerDao = ctx.getBean("singerDao", SingerDao.class);
//
//        var singers = singerDao.
//        assertEquals(3, singers.size());
//        singers.forEach(singer -> LOGGER.info(singer.toString()));
//        ctx.close();
    }
}
