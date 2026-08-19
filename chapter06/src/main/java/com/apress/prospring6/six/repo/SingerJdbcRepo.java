package com.apress.prospring6.six.repo;

import com.apress.prospring6.six.records.Album;
import com.apress.prospring6.six.records.Singer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Repository("singerRepo")
public class SingerJdbcRepo implements SingerRepo {

    private static Logger LOGGER = LoggerFactory.getLogger(SingerJdbcRepo.class);

    private static final String FIND_SINGER_ALBUM = "select s.id, s.first_name, s.last_name, s.birth_date, " +
            "a.id AS album_id, a.title, a.release_date " +
            "from SINGER s " +
            "left join ALBUM a on s.id = a.singer_id";

    private DataSource dataSource;
    private SelectAllSingers selectAllSingers;
    private SelectSingerByFirstName selectSingerByFirstName;
    private UpdateSinger updateSinger;
    private InsertSinger insertSinger;
    private InsertSingerAlbum insertSingerAlbum;
    private StoredFunctionFirstNameById storedFunctionFirstNameById;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.selectAllSingers = new SelectAllSingers(dataSource);
        this.selectSingerByFirstName = new SelectSingerByFirstName(dataSource);
        this.updateSinger = new UpdateSinger(dataSource);
        this.insertSinger = new InsertSinger(dataSource);
        this.insertSingerAlbum = new InsertSingerAlbum(dataSource);
        this.storedFunctionFirstNameById = new StoredFunctionFirstNameById(dataSource);
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    @Override
    public List<Singer> findAll() {
        return selectAllSingers.execute();
    }

    @Override
    public List<Singer> findByFirstName(String firstName) {
        return selectSingerByFirstName.executeByNamedParam(Map.of("first_name", firstName));
    }

    @Override
    public String findNameById(Long id) {
        return "";
    }

    @Override
    public String findLastNameById(Long id) {
        return "";
    }

    @Override
    public Optional<String> findFirstNameById(Long id) {
        var result = storedFunctionFirstNameById.execute(id).get(0);

        return result != null ? Optional.of(storedFunctionFirstNameById.execute(id).get(0)) : Optional.empty();
    }

    @Override
    public List<Singer> findAllWithAlbums() {
        var jdbcTemplate = new JdbcTemplate(getDataSource());

        return jdbcTemplate.query(FIND_SINGER_ALBUM,  rs -> {
            Map<Long, Singer> map = new HashMap<>();
            Singer singer;

            while (rs.next()) {
                var singerID = rs.getLong("id");
                singer = map.computeIfAbsent(singerID, s -> {
                    try {
                        return new Singer(singerID, rs.getString("first_name"),
                                rs.getString("last_name"),
                                rs.getDate("birth_date").toLocalDate(),
                                new ArrayList<>());
                    } catch (SQLException sex) {
                        LOGGER.error("Malformed data!", sex);
                    }

                    return null;
                });

                var albumID = rs.getLong("album_id");
                if (albumID > 0) {

                    var album = new Album(albumID, singerID, rs.getString("title"),
                            rs.getDate("release_date").toLocalDate());

                    Objects.requireNonNull(singer).albums()
                            .add(album);
                }
            }

            return new ArrayList<>(map.values());
        });
    }

    @Override
    public void insert(Singer singer) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        insertSinger.updateByNamedParam(Map.of("first_name", singer.firstName(),
                "last_name", singer.lastName(),
                "birth_date", singer.birthDate()), keyHolder);
        var generatedId = keyHolder.getKey().longValue();
        LOGGER.info("New singer {} {} inserted with id {}", singer.firstName(), singer.lastName(), generatedId);
    }

    @Override
    public void update(Singer singer) {
        updateSinger.updateByNamedParam(
                Map.of("first_name", singer.firstName(),
                        "last_name", singer.lastName(),
                        "birth_date", singer.birthDate(),
                        "id", singer.id())
        );
        LOGGER.info("Existing singer update with id: " + singer.id());
    }

    @Override
    public void delete(Long singerId) {

    }

    @Override
    public void insertWithAlbum(Singer singer) {
        var keyHolder = new GeneratedKeyHolder();
        insertSinger.updateByNamedParam(Map.of(
                "first_name", singer.firstName(),
                "last_name", singer.lastName(),
                "birth_date", singer.birthDate()
        ), keyHolder);
        var newSingerId = Objects.requireNonNull(keyHolder.getKey()).longValue();
        LOGGER.info("New singer {} {} inserted with id {}", singer.firstName(), singer.lastName(), newSingerId);

        var albums = singer.albums();
        if (albums != null) {
            for (Album album : albums) {
                insertSingerAlbum.updateByNamedParam(Map.of(
                        "singer_id", newSingerId,
                        "title", album.title(),
                        "release_date", album.releaseDate()
                ));
            }
        }

        insertSingerAlbum.flush();
    }
}
