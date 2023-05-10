package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import ru.yandex.practicum.filmorate.validator.ValidUserLogin;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class User {
    private int id;
    @NotBlank(message = "Укажите email")
    @Email(message = "Некорректный email")
    private String email;
    @ValidUserLogin
    private String login;
    private String name;
    @PastOrPresent(message = "Укажите корретную дату рождения")
    private LocalDate birthday;
}
