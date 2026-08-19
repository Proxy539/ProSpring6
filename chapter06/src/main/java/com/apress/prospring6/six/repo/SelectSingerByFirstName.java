package com.apress.prospring6.six.repo;

import com.apress.prospring6.six.records.Singer;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

public class SelectSingerByFirstName extends MappingSqlQuery<Singer> {

    private static final String SQL_QUERY = "select id, first_name, last_name, birth_date from SINGER where first_name = :first_name";


    public SelectSingerByFirstName(DataSource dataSource) {
        super(dataSource, SQL_QUERY);
        super.declareParameter(new SqlParameter("first_name", Types.VARCHAR));
    }

    @Override
    protected Singer mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Singer(
                rs.getLong("id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getDate("birth_date").toLocalDate(),
                List.of()
        );
    }
}
