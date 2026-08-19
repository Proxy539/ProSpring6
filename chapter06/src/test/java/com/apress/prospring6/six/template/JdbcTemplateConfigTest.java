package com.apress.prospring6.six.template;

import com.apress.prospring6.six.config.EmbeddedJdbcConfig;
import com.apress.prospring6.six.plain.dao.SingerDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JdbcTemplateConfigTest {

    @Test
    public void testSpringJdbcWithH2Db() {
        var ctx = new AnnotationConfigApplicationContext(SpringJdbcTemplateCfg.class);
        var jdbcTemplate = ctx.getBean("jdbcTemplate", JdbcTemplate.class);
        assertNotNull(jdbcTemplate);

        var singerDao = ctx.getBean("singerDao", SingerDao.class);

        assertEquals("John Mayer", singerDao.findNameById(1L));
        ctx.close();
    }
}
