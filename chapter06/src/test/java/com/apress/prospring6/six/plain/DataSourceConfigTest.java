package com.apress.prospring6.six.plain;

import com.apress.prospring6.six.config.SimpleDataSourceCfg;
import com.apress.prospring6.six.config.SpringDatasourceCfg;
import com.apress.prospring6.six.plain.dao.SingerDao;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DataSourceConfigTest {
    private static Logger LOGGER = LoggerFactory.getLogger(DataSourceConfigTest.class);

    @Test
    public void testSimpleDataSource() throws SQLException {
        var ctx = new AnnotationConfigApplicationContext(SimpleDataSourceCfg.class);
        var dataSource = ctx.getBean("dataSource", DataSource.class);
        assertNotNull(dataSource);
        testDataSource(dataSource);
        ctx.close();
    }

    @Test
    public void testSpringJdbc() throws SQLException {
        var ctx = new AnnotationConfigApplicationContext(SpringDatasourceCfg.class);
        var dataSource = ctx.getBean("dataSource", DataSource.class);
        assertNotNull(dataSource);
        testDataSource(dataSource);
        var singerDao = ctx.getBean("singerDao", SingerDao.class);
        assertEquals("John Mayer", singerDao.findNameById(1L));
        ctx.close();
    }

    private void testDataSource(DataSource dataSource) throws SQLException {
        try (var connection = dataSource.getConnection();
        var statement = connection.prepareStatement("SELECT 1");
        var resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int mockVal = resultSet.getInt("1");
                assertEquals(1, mockVal);
            }
        } catch (Exception e) {
            LOGGER.debug("Something unexpected happened.", e);
        }
    }
}
