package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundObject;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Long, User> users = new HashMap<>();
    private Long idCounter = 0L;

    @Override
    public User addUser(User user) {
        if (users.containsKey(user.getId())) {
            throw new ValidationException("Данный пользователь уже есть в базе");
        }
        Long userId = ++idCounter;
        user.setId(userId);
        users.put(userId, user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        if (!users.containsKey(user.getId())) {
            throw new NotFoundObject("Пользователь", user.getId());
        }
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public List<User> getUsers() {
        List<User> listByUsers = new ArrayList<>(users.values());
        return listByUsers;
    }

    @Override
    public User getUserById(Long id) {
        if (!users.containsKey(id)) {
            throw new NotFoundObject("Пользователь", id);
        }
        return users.get(id);
    }
}


