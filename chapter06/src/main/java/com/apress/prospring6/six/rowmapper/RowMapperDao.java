package com.apress.prospring6.six.rowmapper;


import com.apress.prospring6.six.records.Album;
import com.apress.prospring6.six.records.Singer;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RowMapperDao implements SingerDao {

    private NamedParameterJdbcTemplate namedTemplate;

    public void setNamedTemplate(NamedParameterJdbcTemplate namedTemplate) {
        this.namedTemplate = namedTemplate;
    }

    @Override
    public Set<Singer> findAllWithAlbums() {
        var sqlQuery = "select s.id, s.first_name, s.last_name, s.birth_date, " +
                "a.id AS album_id, a.title, a.release_date " +
                "from SINGER s " +
                "left join ALBUM a on s.id = a.singer_id";
        return new HashSet<>(namedTemplate.query(sqlQuery, rs -> {
            Map<Long, Singer> map = new HashMap<>();
            Singer singer;

            while (rs.next()) {
                Long id = rs.getLong("id");
                singer = map.get(id);
                if (singer == null) {
                    singer = new Singer(id, rs.getString("first_name"), rs.getString("last_name"),
                            rs.getDate("birth_date").toLocalDate(), new ArrayList<>());
                    map.put(id, singer);
                }

                var albumId = rs.getLong("album_id");
                if (albumId > 0) {
                    Album album = new Album(albumId, id, rs.getString("title"),
                            rs.getDate("release_date").toLocalDate());
                    singer.albums().add(album);
                }
            }

            return new HashSet<>(map.values());
        }));
    }
}
