package ru.yandex.practicum.filmorate.ValidatorsTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.FilmController;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FilmControllerTest {
    private Film film;
    private Film film1;
    private Film film2;
    private FilmController controller;

    @BeforeEach
    void createFilm() {
        controller = new FilmController(new FilmService(new InMemoryFilmStorage()));
        film = new Film(1, "Джентльмены удачи", "Заведующий детским садом подменяет собой матерого " +
                "преступника, возглавив его банду, помогая профессору и милиции найти укаденный ими золотой шлем " +
                "Александра Македонского", LocalDate.of(1971, 12, 13), 87);
        film1 = new Film(2, "Фильм с некорректной датой", "Описание",
                LocalDate.of(1800, 1, 1), 50);
        film2 = new Film(2, "Фильм c пограничной датой", "Описание",
                LocalDate.of(1895, 12, 28), 50);
    }

    @Test
    void checkCorrectValidationFilmTest() {
        controller.addFilm(film);
        List<Film> films = controller.getFilms();
        assertEquals(List.of(film), films);
    }

    @Test
    void checkIncorrectValidationFilmTest() {
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> controller.addFilm(film1));
        assertEquals("Некорректная дата релиза", exception.getMessage());
    }

    @Test
    void checkBoundaryValueValidationFilmTest() {
        controller.addFilm(film2);
        List<Film> films = controller.getFilms();
        assertEquals(List.of(film2), films);
    }
}
