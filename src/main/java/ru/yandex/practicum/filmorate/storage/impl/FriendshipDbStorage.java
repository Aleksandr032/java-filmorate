package ru.yandex.practicum.filmorate.storage.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundObject;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FriendshipStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Slf4j
@Component
public class FriendshipDbStorage implements FriendshipStorage {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FriendshipDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addFriend(Long userId, Long friendId) {
        checkUserById(userId);
        checkUserById(friendId);
        String sqlQuery = "INSERT INTO FRIENDSHIPS (USER_ID, FRIEND_ID) VALUES (?, ?)";
        jdbcTemplate.update(sqlQuery,
                userId,
                friendId);
    }

    @Override
    public void removeFriend(Long userId, Long friendId) {
        checkUserById(userId);
        checkUserById(friendId);
        String sqlQuery = "DELETE FROM FRIENDSHIPS WHERE USER_ID = ? and FRIEND_ID = ?";
        jdbcTemplate.update(sqlQuery, userId, friendId);
    }

    @Override
    public List<Long> getFriends(Long id) {
        String sqlQuery = "SELECT FRIEND_ID FROM FRIENDSHIPS WHERE USER_ID = ?";
        return jdbcTemplate.queryForList(sqlQuery, Long.class, id);
    }

    @Override
    public List<Long> getCommonFriends(Long userId, Long otherId) {
        String sqlQuery = "SELECT FRIEND_ID FROM FRIENDSHIPS WHERE USER_ID = ? AND FRIEND_ID IN " +
                "(SELECT FRIEND_ID FROM FRIENDSHIPS WHERE USER_ID = ?)";
        return jdbcTemplate.queryForList(sqlQuery, Long.class, userId, otherId);
    }

    private User makeUser(ResultSet resultSet, int rowNum) throws SQLException {
        return User.builder()
                .id(resultSet.getLong("USER_ID"))
                .email(resultSet.getString("EMAIL"))
                .login(resultSet.getString("LOGIN"))
                .name(resultSet.getString("NAME"))
                .birthday(resultSet.getDate("BIRTHDAY").toLocalDate())
                .build();
    }

    private void checkUserById(Long id) {
        String sqlQuery = "SELECT USER_ID FROM USERS WHERE USER_ID = ?";
        SqlRowSet row = jdbcTemplate.queryForRowSet(sqlQuery, id);
        if (!row.next()) {
            log.warn("Пользователь с идентификатором: " + id + " не найден");
            throw new NotFoundObject("Пользователь", id);
        }
    }
}
