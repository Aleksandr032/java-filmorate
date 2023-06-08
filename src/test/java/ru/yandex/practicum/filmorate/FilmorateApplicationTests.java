package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.impl.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.impl.FriendshipDbStorage;
import ru.yandex.practicum.filmorate.storage.impl.LikeDbStorage;
import ru.yandex.practicum.filmorate.storage.impl.UserDbStorage;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmorateApplicationTests {
    private final UserDbStorage userStorage;
    private final FriendshipDbStorage friendshipStorage;
    private final FilmDbStorage filmStorage;
    private final LikeDbStorage likeStorage;

    @Test
    public void testAddAndGetUser() {
        userStorage.addUser(makeUser());
        assertEquals(1, userStorage.getUsers().size());
        assertEquals("Name", userStorage.getUserById(1L).getName());
    }

    @Test
    public void testUpdateUser() {
        userStorage.updateUser(forUpdatedUser());
        assertEquals("newName", userStorage.getUserById(1L).getName());
    }

    @Test
    public void testGetAllUser() {
        userStorage.addUser(makeUser());
        List<User> users = userStorage.getUsers();
        assertEquals(2, users.size());
    }

    @Test
    public void testGetUserById() {
        User user = userStorage.getUserById(2L);
        assertEquals("Name", user.getName());
    }

    @Test
    public void testAddAndGetFriend() {
        userStorage.addUser(makeFriend());
        friendshipStorage.addFriend(1L, 2L);
        friendshipStorage.addFriend(1L, 3L);
        List<Long> friends = friendshipStorage.getFriends(1L);
        assertEquals(2, friends.size());
    }

    @Test
    public void testAddAndGetFilm() {
        filmStorage.addFilm(makeFilm());
        assertEquals(1, filmStorage.getFilms().size());
        assertEquals("Название", filmStorage.getFilmById(1L).getName());
    }

    @Test
    public void testUpdateFilm() {
        filmStorage.updateFilm(forUpdatedFilm());
        assertEquals("Новое название", filmStorage.getFilmById(1L).getName());
        assertEquals(5L, filmStorage.getFilmById(1L).getMpa().getId());
    }

    @Test
    public void testGetAllFilms() {
        filmStorage.addFilm(makeFilm());
        List<Film> films = filmStorage.getFilms();
        assertEquals(2, films.size());
    }

    @Test
    public void testGetFilmById() {
        Film film = filmStorage.getFilmById(2L);
        assertEquals("Название", film.getName());
        assertEquals("R", film.getMpa().getName());
    }

    @Test
    public void testAddLikeToFilm() {
        likeStorage.addLike(2L, 2L);
        likeStorage.addLike(2L, 1L);
        likeStorage.addLike(1L, 1L);
        List<Film> popularFilms = likeStorage.getFilmsPopular(2L);
        Film film = popularFilms.get(0);
        assertEquals(2L, film.getId());
        assertEquals(2, popularFilms.size());
    }

    private User makeUser() {
        return User.builder()
                .login("login")
                .email("email@mail.ru")
                .name("Name")
                .birthday(LocalDate.of(1993, 3, 16))
                .build();
    }

    private User forUpdatedUser() {
        return User.builder()
                .id(1L)
                .login("newLogin")
                .email("newEmail@mail.ru")
                .name("newName")
                .birthday(LocalDate.of(1990, 5, 10))
                .build();
    }

    private User makeFriend() {
        return User.builder()
                .login("friend")
                .email("friend@mail.ru")
                .name("name friend")
                .birthday(LocalDate.of(1990, 12, 31))
                .build();
    }

    private Film makeFilm() {
        return Film.builder()
                .name("Название")
                .description("Описание")
                .releaseDate(LocalDate.of(2000, 10, 20))
                .duration(50)
                .mpa(Mpa.builder().id(4L).name("R").build())
                .build();
    }

    private Film forUpdatedFilm() {
        return Film.builder()
                .id(1L)
                .name("Новое название")
                .description("Новое описание")
                .releaseDate(LocalDate.of(2000, 10, 21))
                .duration(60)
                .mpa(Mpa.builder().id(5L).name("NC-17").build())
                .build();
    }
}
