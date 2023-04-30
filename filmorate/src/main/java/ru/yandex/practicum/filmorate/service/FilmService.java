package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.time.LocalDate;
import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class FilmService {
    private FilmStorage filmStorage;

    public Film addFilm(Film film) {
        // checkValidReleaseDate(film, "добавлен");
        return filmStorage.addFilm(film);
    }

    public Film updateFilm(Film film) {
        //checkValidReleaseDate(film, "добавлен");
        return filmStorage.updateFilm(film);
    }

    public List<Film> getFilms() {
        return filmStorage.getFilms();
    }

    public void checkValidReleaseDate(Film film, String status) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("Некорректная дата релиза");
        }
        log.debug("Фильм: {} {}", film.getName(), status);
    }
}
