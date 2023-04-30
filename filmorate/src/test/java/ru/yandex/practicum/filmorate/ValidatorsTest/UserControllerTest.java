package ru.yandex.practicum.filmorate.ValidatorsTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserControllerTest {
    private User user;
    private User user1;
    private User user2;
    private UserController controller;

    @BeforeEach
    void createUser() {
        controller = new UserController(new UserService(new InMemoryUserStorage()));
        user = new User();
        user.setId(1);
        user.setEmail("allCorrect@yandex.ru");
        user.setLogin("Sven");
        user.setName("Евпатий");
        user.setBirthday(LocalDate.of(1200, 1, 1));

        user1 = new User();
        user1.setId(2);
        user1.setEmail("withoutName@yandex.ru");
        user1.setLogin("Pudge");
        user1.setBirthday(LocalDate.of(1200, 1, 1));

        user2 = new User();
        user2.setId(3);
        user2.setEmail("nameIsEmpty@yandex.ru");
        user2.setLogin("Legion Commander");
        user2.setName(" ");
        user2.setBirthday(LocalDate.of(1200, 1, 1));
    }

    @Test
    void checkCorrectValidationUserTest() {
        controller.addUser(user);
        List<User> users = controller.getUsers();
        assertEquals(List.of(user), users);
    }

    @Test
    void checkWithoutNameUserTest() {
        controller.addUser(user1);
        assertEquals("Pudge", user1.getName());
    }

    @Test
    void checkNameIsEmptyUserTest() {
        controller.addUser(user2);
        assertEquals("Legion Commander", user2.getName());
    }
}



