package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films = new HashMap<>();
    private int idCounter = 0;

    @Override
    public Film addFilm(Film film) {
        if (films.containsKey(film.getId())) {
            throw new ValidationException("Данный фильм уже есть в базе");
        }
        int filmId = ++idCounter;
        film.setId(filmId);
        films.put(filmId, film);
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        if (!films.containsKey(film.getId())) {
            throw new ValidationException("Данного фильма ещё нет в базе");
        }
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public List<Film> getFilms() {
        List<Film> listByFilms = new ArrayList<>(films.values());
        return listByFilms;
    }
}
