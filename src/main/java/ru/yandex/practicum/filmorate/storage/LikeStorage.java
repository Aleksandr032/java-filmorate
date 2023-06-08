package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface LikeStorage {
    void addLike(Long film, Long user);

    void removeLike(Long film, Long user);

    List<Film> getFilmsPopular(Long count);
}
