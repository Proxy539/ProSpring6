package com.apress.prospring6.six.hybrid;

import com.apress.prospring6.six.MariaDbErrorCodesTranslator;
import com.apress.prospring6.six.plain.dao.SingerDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.JdkRegexpMethodPointcut;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;

public class JdbcSingerDao implements SingerDao, InitializingBean {

    private static Logger LOGGER = LoggerFactory.getLogger(JdbcSingerDao.class);
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        var jdbcTempalte = new JdbcTemplate();
        jdbcTempalte.setDataSource(dataSource);

        var errorTranslator = new MariaDbErrorCodesTranslator();
        errorTranslator.setDataSource(dataSource);

        jdbcTempalte.setExceptionTranslator(errorTranslator);
        this.jdbcTemplate = jdbcTempalte;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (dataSource == null) {
            throw new BeanCreationException("Must set datasource on SingerDao");
        }
    }

    @Override
    public String findNameById(Long id) {
        var result = "";
        try (var connection = dataSource.getConnection();
             var statement = connection.prepareStatement("select first_name, last_name from SINGER where id = " + id);
             var resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                return resultSet.getString("first_name") + " " + resultSet.getString("last_name");
            }
        } catch (SQLException ex) {
            LOGGER.error("Problem when executing SELECT!", ex);
        }

        return result;
    }
}
