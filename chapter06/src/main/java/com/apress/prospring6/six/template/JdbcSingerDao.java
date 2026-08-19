package com.apress.prospring6.six.template;

import com.apress.prospring6.six.plain.dao.SingerDao;
import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcSingerDao implements SingerDao {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String findNameById(Long id) {
        return jdbcTemplate
                .queryForObject("select CONCAT(first_name, ' ', last_name) from SINGER where id = ?", String.class, id);
    }
}
