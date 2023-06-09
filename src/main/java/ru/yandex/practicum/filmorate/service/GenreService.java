package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundObject;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.util.List;

@Service
public class GenreService {
    private final GenreStorage genreStorage;

    public GenreService(@Qualifier("genreDbStorage") GenreStorage genreStorage) {
        this.genreStorage = genreStorage;
    }

    public Genre getGenreById(Long id) throws NotFoundObject {
        final Genre genre = genreStorage.getGenreById(id);
        if (genre == null) {
            throw new NotFoundObject("Жанр", id);
        }
        return genre;
    }

    public List<Genre> getAllGenres() {
        return genreStorage.getAllGenres();
    }
}