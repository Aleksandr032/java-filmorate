package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FriendshipStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {
    private final UserStorage userStorage;
    private final FriendshipStorage friendshipStorage;

    public UserService(@Qualifier("userDbStorage") UserStorage userStorage,
                       @Qualifier("friendshipDbStorage") FriendshipStorage friendshipStorage) {
        this.userStorage = userStorage;
        this.friendshipStorage = friendshipStorage;
    }

    public User addUser(User user) {
        return userStorage.addUser(user);
    }

    public User updateUser(User user) {
        useLoginAsName(user, "обновлен");
        return userStorage.updateUser(user);
    }

    public List<User> getUsers() {
        return userStorage.getUsers();
    }

    public User getUserById(Long id) {
        return userStorage.getUserById(id);
    }

    public void useLoginAsName(User user, String status) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        log.debug("Пользователь с id: {} - {}. Имя пользователя {}", user.getId(), status, user.getName());
    }

    public List<User> getCommonFriends(Long userId, Long otherId) {
        return friendshipStorage.getCommonFriends(userId, otherId).stream()
                .map(userStorage::getUserById)
                .collect(Collectors.toList());
    }

    public void addFriend(Long userId, Long friendId) {
        friendshipStorage.addFriend(userId, friendId);
    }

    public void removeFriend(Long userId, Long friendId) {
        friendshipStorage.removeFriend(userId, friendId);
    }

    public List<User> getFriends(Long userId) {
        return friendshipStorage.getFriends(userId).stream()
                .map(userStorage::getUserById)
                .collect(Collectors.toList());
    }
}
