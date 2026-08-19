package com.apress.prospring6.six.config;

import com.apress.prospring6.six.plain.JdbcSingerDao;
import com.apress.prospring6.six.plain.dao.SingerDao;
import com.apress.prospring6.six.repo.SingerJdbcRepo;
import com.apress.prospring6.six.repo.SingerRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;

@Import(BasicDataSourceCfg.class)
@Configuration
public class SpringDatasourceCfg {

    private static Logger LOGGER = LoggerFactory.getLogger(SpringDatasourceCfg.class);

    @Autowired
    DataSource dataSource;

    @Bean
    public SingerDao singerDao() {
        JdbcSingerDao dao = new JdbcSingerDao();
        dao.setDataSource(dataSource);
        return dao;
    }

    @Bean
    public SingerRepo singerRepo() {
        final var singerJdbcRepo = new SingerJdbcRepo();
        singerJdbcRepo.setDataSource(dataSource);
        return singerJdbcRepo;
    }
}
