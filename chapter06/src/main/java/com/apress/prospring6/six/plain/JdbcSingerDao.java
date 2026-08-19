package com.apress.prospring6.six.plain;

import com.apress.prospring6.six.plain.dao.SingerDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;

import javax.sql.DataSource;
import java.sql.SQLException;

public class JdbcSingerDao implements SingerDao, InitializingBean {

    private static Logger LOGGER = LoggerFactory.getLogger(JdbcSingerDao.class);
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
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
