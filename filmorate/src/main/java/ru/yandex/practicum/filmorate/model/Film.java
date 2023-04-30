package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ru.yandex.practicum.filmorate.validator.ValidReleaseDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Film {
    private int id;
    @NotBlank(message = "Укажите название фильма")
    private String name;

    @Length(min = 1, max = 200, message = "Описание фильма должно иметь не менее 1 символа и не более 200")
    private String description;

    @ValidReleaseDate(message = "Некорректная дата")
    private LocalDate releaseDate;
    @Positive(message = "Продолжительнсть фильма не может быть отрицательной или 0")
    private int duration;
}

