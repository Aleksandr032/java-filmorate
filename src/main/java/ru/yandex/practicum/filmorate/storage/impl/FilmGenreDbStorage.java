package ru.yandex.practicum.filmorate.storage.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.storage.FilmGenreStorage;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class FilmGenreDbStorage implements FilmGenreStorage {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FilmGenreDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addGenre(Long filmId, Long genreId) {
        String sqlQuery = "INSERT INTO FILM_GENRES (FILM_ID, GENRE_ID) VALUES (?, ?)";
        jdbcTemplate.update(sqlQuery, filmId, genreId);
    }

    @Override
    public Set<Long> getGenreByFilmId(Long filmId) {
        String sqlQuery = "SELECT GENRE_ID FROM FILM_GENRES WHERE FILM_ID = ?";
        List<Long> genreList = jdbcTemplate.queryForList(sqlQuery, Long.class, filmId);
        return new HashSet<>(genreList);
    }

    @Override
    public void removeGenreByFilmId(Long filmId) {
        String sqlQuery = "DELETE FROM FILM_GENRES WHERE FILM_ID = ?";
        jdbcTemplate.update(sqlQuery, filmId);
    }
}
