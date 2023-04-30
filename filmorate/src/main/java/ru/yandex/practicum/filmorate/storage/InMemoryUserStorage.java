package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> users = new HashMap<>();
    private int idCounter = 0;

    @Override
    public User addUser(User user) {
        if (users.containsKey(user.getId())) {
            throw new ValidationException("Данный пользователь уже есть в базе");
        }
        int userId = ++idCounter;
        user.setId(userId);
        users.put(userId, user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        if (!users.containsKey(user.getId())) {
            throw new ValidationException("Данного пользователя ещё нет в базе");
        }
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public List<User> getUsers() {
        List<User> listByUsers = new ArrayList<>(users.values());
        return listByUsers;
    }
}
