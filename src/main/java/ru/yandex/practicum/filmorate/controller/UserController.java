package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@Slf4j
public class UserController {
    private UserService userService;

    @PostMapping
    public User addUser(@Valid @RequestBody User user) {
        userService.useLoginAsName(user, "добавлен");
        return userService.addUser(user);
    }

    @GetMapping
    public List<User> getUsers() {
        log.debug("Общее количество пользователей: {}", userService.getUsers().size());
        return userService.getUsers();
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        userService.useLoginAsName(user, "обновлен");
        return userService.updateUser(user);
    }
}
