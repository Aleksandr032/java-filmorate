package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundObject;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.MpaStorage;

import java.util.List;

@Service
public class MpaService {
    private final MpaStorage mpaStorage;

    public MpaService(@Qualifier("mpaDbStorage") MpaStorage mpaStorage) {
        this.mpaStorage = mpaStorage;
    }

    public Mpa getMpaRatingById(Long id) throws NotFoundObject {
        final Mpa mpa = mpaStorage.getMpaRatingById(id);
        if (mpa == null) {
            throw new NotFoundObject("Mpa", id);
        }
        return mpa;
    }

    public List<Mpa> getAllMpaRatings() {
        return mpaStorage.getAllMpaRatings();
    }
}
