package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exception.IncorrectParameterException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MpaController {
    private final MpaService mpaService;

    @GetMapping("/mpa/{mpaId}")
    public Mpa getMpaRatingById(@PathVariable Long mpaId) {
        checkId(mpaId);
        return mpaService.getMpaRatingById(mpaId);
    }

    @GetMapping("/mpa")
    public List<Mpa> getAllMpaRatings() {
        return mpaService.getAllMpaRatings();
    }

    private void checkId(Long id) {
        if (id < 0) throw new IncorrectParameterException("mpa_id не может быть отрицательным");
    }
}