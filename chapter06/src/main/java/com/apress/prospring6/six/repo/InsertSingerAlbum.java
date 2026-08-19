package com.apress.prospring6.six.repo;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.BatchSqlUpdate;

import javax.sql.DataSource;
import java.sql.Types;

public class InsertSingerAlbum extends BatchSqlUpdate {

    private static final int BATCH_SIZE = 10;
    private static final String INSERT_SINGER_ALBUM = "insert into ALBUM (singer_id, title, release_date) " +
            "values (:singer_id, :title, :release_date)";

    public InsertSingerAlbum(DataSource dataSource) {
        super(dataSource, INSERT_SINGER_ALBUM);
        declareParameter(new SqlParameter("singer_id", Types.INTEGER));
        declareParameter(new SqlParameter("title", Types.VARCHAR));
        declareParameter(new SqlParameter("release_date", Types.DATE));
        setBatchSize(BATCH_SIZE);
    }
}
