package com.apress.prospring6.six.repo;

import com.apress.prospring6.six.records.Singer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("singerRepo")
public class SingerJdbcRepo implements SingerRepo {

    private static final Logger LOGGER = LoggerFactory.getLogger(SingerJdbcRepo.class);
    public static final String ALL_SELECT = "select * from SINGER";
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Singer> findAll() {
        return jdbcTemplate.queryForStream(ALL_SELECT, (rs, rowNum) -> new Singer(rs.getLong("id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getDate("birth_date").toLocalDate(),
                List.of()))
                .toList();
    }

    @Override
    public List<Singer> findByFirstName(String firstName) {
        return List.of();
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
        return Optional.empty();
    }

    @Override
    public List<Singer> findAllWithAlbums() {
        return List.of();
    }

    @Override
    public void insert(Singer singer) {

    }

    @Override
    public void update(Singer singer) {

    }

    @Override
    public void delete(Long singerId) {

    }

    @Override
    public void insertWithAlbum(Singer singer) {

    }
}
