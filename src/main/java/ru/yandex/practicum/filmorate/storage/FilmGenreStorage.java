package ru.yandex.practicum.filmorate.storage;

import java.util.Set;

public interface FilmGenreStorage {
    void addGenre(Long filmId, Long genreId);

    Set<Long> getGenreByFilmId(Long filmId);

    void removeGenreByFilmId(Long filmId);
}
