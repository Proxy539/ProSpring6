package com.apress.prospring6.six.repo;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlFunction;

import javax.sql.DataSource;
import java.sql.Types;

public class StoredFunctionFirstNameById extends SqlFunction<String> {
    private static final String SQL_CALL = "select getFirstNameById(?)";

    public StoredFunctionFirstNameById(DataSource dataSource) {
        super(dataSource, SQL_CALL);
        declareParameter(new SqlParameter(Types.INTEGER));
        compile();
    }
}
