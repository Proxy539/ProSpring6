package com.apress.prospring6.six;

import org.jspecify.annotations.Nullable;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DeadlockLoserDataAccessException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;

import java.sql.SQLException;

public class MariaDbErrorCodesTranslator extends SQLErrorCodeSQLExceptionTranslator {

    @Override
    protected @Nullable DataAccessException customTranslate(String task, @Nullable String sql, SQLException sqlEx) {
        if (sqlEx.getErrorCode() == -12345) {
            return new DeadlockLoserDataAccessException(task, sqlEx);
        }
        return null;
    }
}
