package ru.yandex.practicum.filmorate.storage;

import java.util.List;

public interface FriendshipStorage {
    void addFriend(Long user, Long toUser);

    void removeFriend(Long user, Long fromUser);

    List<Long> getFriends(Long user);

    List<Long> getCommonFriends(Long userId, Long otherId);
}
