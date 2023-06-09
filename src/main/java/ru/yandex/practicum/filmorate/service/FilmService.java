package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundObject;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.*;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final LikeStorage likeStorage;
    private final FilmGenreStorage filmGenreStorage;
    private final GenreStorage genreStorage;

    public FilmService(@Qualifier("filmDbStorage") FilmStorage filmStorage,
                       @Qualifier("userDbStorage") UserStorage userStorage,
                       @Qualifier("likeDbStorage") LikeStorage likeStorage,
                       @Qualifier("filmGenreDbStorage") FilmGenreStorage filmGenreStorage,
                       @Qualifier("genreDbStorage") GenreStorage genreStorage) {
        this.userStorage = userStorage;
        this.filmStorage = filmStorage;
        this.likeStorage = likeStorage;
        this.filmGenreStorage = filmGenreStorage;
        this.genreStorage = genreStorage;
    }

    public Film addFilm(Film film) {
        Film newFilm = filmStorage.addFilm(film);
        film.setId(newFilm.getId());
        if (film.getGenres() != null) {
            for (Genre genre : film.getGenres()) {
                filmGenreStorage.addGenre(film.getId(), genre.getId());
            }
        }
        return film;
    }

    public Film updateFilm(Film film) {
        filmGenreStorage.removeGenreByFilmId(film.getId());
        filmStorage.updateFilm(film);
        if (film.getGenres() != null) {
            for (Genre genre : film.getGenres()) {
                filmGenreStorage.addGenre(film.getId(), genre.getId());
            }
        }
        return film;
    }

    public List<Film> getFilms() {
        return filmStorage.getFilms();
    }

    public Film getFilmById(Long id) {
        Film film = filmStorage.getFilmById(id);
        if (film == null) {
            throw new NotFoundObject("Фильма", id);
        } else {
            Set<Long> genresId = filmGenreStorage.getGenreByFilmId(id);
            LinkedHashSet<Genre> genres = new LinkedHashSet<>();

            for (Long genreId : genresId) {
                genres.add(genreStorage.getGenreById(genreId));
            }
            film.setGenres(genres);
        }
        return film;
    }

    public void addLike(Long filmId, Long userId) {
        checkUser(userId);
        checkFilm(filmId);
        likeStorage.addLike(filmId, userId);
    }

    public void removeLike(Long filmId, Long userId) {
        checkUser(userId);
        checkFilm(filmId);
        likeStorage.removeLike(filmId, userId);
    }

    public List<Film> getFilmsPopular(Long count) {
        List<Film> popularFilms = likeStorage.getFilmsPopular(count);
        return popularFilms.subList(0, popularFilms.size());
    }

    private void checkFilm(Long id) {
        if (filmStorage.getFilmById(id) == null) {
            log.warn("Фильм с идентификатором: " + id + " не найден");
            throw new NotFoundObject("Фильм", id);
        }
    }

    private void checkUser(Long id) {
        if (userStorage.getUserById(id) == null) {
            log.warn("Пользователь с идентификатором: " + id + " не найден");
            throw new NotFoundObject("Пользователь", id);
        }
    }
}
