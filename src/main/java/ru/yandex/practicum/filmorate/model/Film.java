package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import ru.yandex.practicum.filmorate.validator.ValidReleaseDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Film {
    private Long id;
    @NotBlank(message = "Укажите название фильма")
    private String name;

    @NotBlank(message = "Укажите описание фильма")
    @Length(min = 1, max = 200, message = "Описание фильма должно иметь не менее 1 символа и не более 200")
    private String description;

    @NotNull(message = "Укажите дату релиза фильма")
    @ValidReleaseDate
    private LocalDate releaseDate;
    @Positive(message = "Продолжительнсть фильма не может быть отрицательной или 0")
    private int duration;
    private Set<Long> likes = new HashSet<>();
}

