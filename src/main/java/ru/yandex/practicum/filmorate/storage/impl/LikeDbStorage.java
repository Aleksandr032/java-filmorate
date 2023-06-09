package ru.yandex.practicum.filmorate.storage.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.LikeStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class LikeDbStorage implements LikeStorage {
    private final JdbcTemplate jdbcTemplate;

    public LikeDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addLike(Long filmId, Long userId) {
        String sqlQuery = "INSERT INTO LIKES (FILM_ID, USER_ID) values (?, ?)";
        jdbcTemplate.update(sqlQuery, filmId, userId);
    }

    @Override
    public void removeLike(Long filmId, Long userId) {
        String sqlQuery = "DELETE FROM LIKES WHERE FILM_ID = ? AND USER_ID = ?";
        jdbcTemplate.update(sqlQuery, filmId, userId);
    }

    @Override
    public List<Film> getFilmsPopular(Long count) {
        String sqlQuery = "SELECT f.FILM_ID, f.FILM_NAME, f.DESCRIPTION, f.RELEASE_DATE, f.DURATION, f.MPA_ID, "
                + "COUNT(l.USER_ID) AS like_rate FROM FILMS f LEFT OUTER JOIN LIKES l ON f.FILM_ID = l.FILM_ID "
                + "GROUP BY f.FILM_ID ORDER BY like_rate DESC LIMIT ?";
        return jdbcTemplate.query(sqlQuery, this::makeFilm, count);
    }

    private Film makeFilm(ResultSet resultSet, int rowNum) throws SQLException {
        Long mpaId = resultSet.getLong("MPA_ID");
        String sqlFindName = "SELECT MPA_ID, NAME FROM MPA WHERE MPA_ID = ?";
        Mpa mpa = jdbcTemplate.queryForObject(sqlFindName, this::makeMpa, mpaId);

        return Film.builder()
                .id(resultSet.getLong("FILM_ID"))
                .name(resultSet.getString("FILM_NAME"))
                .description(resultSet.getString("DESCRIPTION"))
                .releaseDate(resultSet.getDate("RELEASE_DATE").toLocalDate())
                .duration(resultSet.getInt("DURATION"))
                .mpa(mpa)
                .build();
    }

    private Mpa makeMpa(ResultSet resultSet, int rowNum) throws SQLException {
        return Mpa.builder()
                .id(resultSet.getLong("MPA_ID"))
                .name(resultSet.getString("NAME"))
                .build();
    }
}
