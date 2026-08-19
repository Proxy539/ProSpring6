package com.apress.prospring6.six.named;

import com.apress.prospring6.six.plain.dao.SingerDao;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.Map;

public class NamedTemplateDao implements SingerDao {

    private NamedParameterJdbcTemplate namedTemplate;

    public void setNamedTemplate(NamedParameterJdbcTemplate namedTemplate) {
        this.namedTemplate = namedTemplate;
    }

    @Override
    public String findNameById(Long id) {
        return namedTemplate.queryForObject("select CONCAT(first_name, ' ', last_name) from SINGER where id = :singerId", Map.of("singerId", id), String.class);
    }
}
