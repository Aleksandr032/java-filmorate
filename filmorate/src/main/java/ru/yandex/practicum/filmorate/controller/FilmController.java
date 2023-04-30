package ru.yandex.practicum.filmorate.controller;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/films")
@AllArgsConstructor
@Slf4j
public class FilmController {
    private FilmService filmService;

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        filmService.checkValidReleaseDate(film, "добавлен");
        return filmService.addFilm(film);
    }

    @GetMapping
    public List<Film> getFilms() {
        log.debug("Общее количество фильмов: {}", filmService.getFilms().size());
        return filmService.getFilms();
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) {
        filmService.checkValidReleaseDate(film, "обновлен");
        return filmService.updateFilm(film);
    }
}
