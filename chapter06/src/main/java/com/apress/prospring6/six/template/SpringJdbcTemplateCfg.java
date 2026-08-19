package com.apress.prospring6.six.template;

import com.apress.prospring6.six.config.BasicDataSourceCfg;
import com.apress.prospring6.six.plain.dao.SingerDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Import(BasicDataSourceCfg.class)
@Configuration
public class SpringJdbcTemplateCfg {

    private static Logger LOGGER = LoggerFactory.getLogger(SpringJdbcTemplateCfg.class);

    @Autowired
    DataSource dataSource;

    @Bean
    public SingerDao singerDao() {
        JdbcSingerDao dao = new JdbcSingerDao();
        dao.setJdbcTemplate(jdbcTemplate());
        return dao;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
        return jdbcTemplate;
    }
}
