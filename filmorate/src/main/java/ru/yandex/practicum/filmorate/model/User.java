package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import ru.yandex.practicum.filmorate.validator.ValidUserLogin;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Data
public class User {
    private int id;

    @NotNull(message = "Укажите email")
    @Email(message = "Некорректный email")
    private String email;
    @NotNull(message = "Укажите логин")
    @ValidUserLogin
    private String login;
    private String name;
    @PastOrPresent(message = "Укажите корретную дату рождения")
    private LocalDate birthday;

}
