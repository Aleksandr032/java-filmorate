package ru.yandex.practicum.filmorate.storage.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundObject;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.FilmGenreStorage;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Slf4j
@Component
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;
    private final FilmGenreStorage filmGenreStorage;
    private final GenreStorage genreStorage;

    public FilmDbStorage(@Qualifier("filmGenreDbStorage") FilmGenreStorage filmGenreStorage,
                         @Qualifier("genreDbStorage") GenreStorage genreStorage,
                         JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.filmGenreStorage = filmGenreStorage;
        this.genreStorage = genreStorage;
    }

    @Override
    public Film addFilm(Film film) {
        String sqlQuery = "INSERT INTO FILMS (FILM_NAME, DESCRIPTION, RELEASE_DATE, DURATION, MPA_ID) " +
                "values (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"FILM_ID"});
            stmt.setString(1, film.getName());
            stmt.setString(2, film.getDescription());
            stmt.setDate(3, Date.valueOf(film.getReleaseDate()));
            stmt.setInt(4, film.getDuration());
            stmt.setLong(5, film.getMpa().getId());
            return stmt;
        }, keyHolder);
        Long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
        film.setId(id);
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        String sqlQuery = "UPDATE FILMS SET FILM_NAME = ?, DESCRIPTION = ?, RELEASE_DATE = ?, DURATION = ?, MPA_ID = ? "
                + "WHERE FILM_ID = ?";
        jdbcTemplate.update(sqlQuery,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId(),
                film.getId());
        return getFilmById(film.getId());
    }

    @Override
    public List<Film> getFilms() {
        String sqlQuery = "SELECT * FROM FILMS";
        return jdbcTemplate.query(sqlQuery, this::makeFilm);
    }

    private Film makeFilm(ResultSet resultSet, int rowNum) throws SQLException {
        Long mpaId = resultSet.getLong("MPA_ID");
        String sqlFindName = "SELECT MPA_ID, NAME FROM MPA WHERE MPA_ID = ?";
        Mpa mpa = jdbcTemplate.queryForObject(sqlFindName, this::makeMpa, mpaId);

        Film film = new Film();
        film.setId(resultSet.getLong("FILM_ID"));
        film.setName(resultSet.getString("FILM_NAME"));
        film.setDescription(resultSet.getString("DESCRIPTION"));
        film.setReleaseDate(resultSet.getDate("RELEASE_DATE").toLocalDate());
        film.setDuration(resultSet.getInt("DURATION"));
        film.setMpa(mpa);

        Long filmId = film.getId();
        Set<Long> genresId = filmGenreStorage.getGenreByFilmId(filmId);
        Set<Genre> genres = new TreeSet<>(Comparator.comparingLong(Genre::getId));
        for (Long genreId : genresId) {
            genres.add(genreStorage.getGenreById(genreId));
        }
        film.setGenres(genres);

        return film;
    }

    private Mpa makeMpa(ResultSet resultSet, int rowNum) throws SQLException {
        return Mpa.builder()
                .id(resultSet.getLong("MPA_ID"))
                .name(resultSet.getString("NAME"))
                .build();
    }

    private Genre makeGenre(ResultSet resultSet, int rowNum) throws SQLException {
        return Genre.builder()
                .id(resultSet.getLong("GENRE_ID"))
                .name(resultSet.getString("NAME"))
                .build();
    }

    @Override
    public Film getFilmById(Long id) {
        checkFilmById(id);
        String sqlQuery = "SELECT FILM_ID, FILM_NAME, DESCRIPTION, RELEASE_DATE, DURATION, MPA_ID FROM FILMS WHERE FILM_ID = ?";
        SqlRowSet row = jdbcTemplate.queryForRowSet(sqlQuery, id);
        if (row.next()) {
            return jdbcTemplate.queryForObject(sqlQuery, this::makeFilm, id);
        } else {
            return null;
        }
    }

    public void checkFilmById(Long id) {
        String sqlQuery = "SELECT FILM_ID FROM FILMS WHERE FILM_ID = ?";
        SqlRowSet row = jdbcTemplate.queryForRowSet(sqlQuery, id);
        if (!row.next()) {
            log.warn("Фильм с идентификатором: " + id + " не найден");
            throw new NotFoundObject("Фильм", id);
        }
    }
}
