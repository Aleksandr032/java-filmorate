package ru.yandex.practicum.filmorate.ValidatorsTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FilmControllerTest {
    private Validator validator;
    private Film film;
    private Film film1;
    private Film film2;
    private Film film3;
    private Film film4;

    @BeforeEach
    void createFilm() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        film = new Film();
        film.setId(1);
        film.setName("Джентльмены удачи");
        film.setDescription("Заведующий детским садом подменяет собой матерого преступника, возглавив его банду, " +
                "помогая профессору и милиции найти укаденный ими золотой шлем Александра Македонского");
        film.setReleaseDate(LocalDate.of(1971, 12, 13));
        film.setDuration(87);

        film1 = new Film();
        film1.setId(2);
        film1.setName("Фильм с некорректной датой");
        film1.setDescription("Описание");
        film1.setReleaseDate(LocalDate.of(1800, 1, 1));
        film1.setDuration(50);

        film2 = new Film();
        film2.setId(3);
        film2.setName("Фильм c пограничной датой");
        film2.setDescription("Описание");
        film2.setReleaseDate(LocalDate.of(1895, 12, 28));
        film2.setDuration(50);

        film3 = new Film();
        film3.setId(4);
        film3.setDescription("Фильм без имени");
        film3.setReleaseDate(LocalDate.of(1895, 12, 28));
        film3.setDuration(50);

        film4 = new Film();
        film4.setId(5);
        film4.setName("Фильм без описания");
        film4.setReleaseDate(LocalDate.of(2000, 12, 28));
        film4.setDuration(50);
    }

    @Test
    void checkCorrectValidationFilmTest() {
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(0, violations.size());
    }

    @Test
    void checkIncorrectValidationFilmTest() {
        Set<ConstraintViolation<Film>> violations = validator.validate(film1);
        assertEquals(1, violations.size());
    }

    @Test
    void checkBoundaryValueValidationFilmTest() {
        Set<ConstraintViolation<Film>> violations = validator.validate(film2);
        assertEquals(0, violations.size());
    }

    @Test
    void checkWithoutNameFilmTest() {
        Set<ConstraintViolation<Film>> violations = validator.validate(film3);
        assertEquals(1, violations.size());
    }

    @Test
    void checkWithoutDescriptionFilmTest() {
        Set<ConstraintViolation<Film>> violations = validator.validate(film4);
        assertEquals(1, violations.size());
    }
}
