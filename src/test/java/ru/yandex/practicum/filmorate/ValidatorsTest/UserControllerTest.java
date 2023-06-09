package ru.yandex.practicum.filmorate.ValidatorsTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserControllerTest {
    UserController userController;
    private Validator validator;
    private User user;
    private User user1;
    private User user2;
    private User user3;
    private User user4;
    private User user5;
    private User user6;
    private User user7;

    @BeforeEach
    void createUser() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        userController = new UserController(new UserService(new InMemoryUserStorage()));

        user = new User();
        user.setId(1L);
        user.setEmail("allCorrect@yandex.ru");
        user.setLogin("Sven");
        user.setName("Евпатий");
        user.setBirthday(LocalDate.of(1200, 1, 1));

        user1 = new User();
        user1.setId(2L);
        user1.setEmail("withoutName@yandex.ru");
        user1.setLogin("Pudge");
        user1.setBirthday(LocalDate.of(1200, 1, 1));

        user2 = new User();
        user2.setId(3L);
        user2.setEmail("nameIsEmpty@yandex.ru");
        user2.setLogin("LegionCommander");
        user2.setName(" ");
        user2.setBirthday(LocalDate.of(1200, 1, 1));

        user3 = new User();
        user3.setId(4L);
        user3.setEmail("withoutLogin@yandex.ru");
        user3.setName("Puck");
        user3.setBirthday(LocalDate.of(1200, 1, 1));

        user4 = new User();
        user4.setId(5L);
        user4.setEmail("loginWithBlank@yandex.ru");
        user4.setLogin("Legion Commander");
        user4.setName("Puck");
        user4.setBirthday(LocalDate.of(1200, 1, 1));

        user5 = new User();
        user5.setId(6L);
        user5.setEmail("allCorrect@yandex.ru");
        user5.setLogin("Пятый");
        user5.setName("Пятачок");
        user5.setBirthday(LocalDate.of(1200, 1, 1));

        user6 = new User();
        user6.setId(7L);
        user6.setEmail("allCorrect@yandex.ru");
        user6.setLogin("Шестой");
        user6.setName("Винни-Пух");
        user6.setBirthday(LocalDate.of(1200, 1, 1));

        user7 = new User();
        user7.setId(8L);
        user7.setEmail("allCorrect@yandex.ru");
        user7.setLogin("Седьмой");
        user7.setName("Иа");
        user7.setBirthday(LocalDate.of(1200, 1, 1));
    }

    @Test
    void checkCorrectValidationUserTest() {
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(0, violations.size());
    }

    @Test
    void checkWithoutNameUserTest() {
        Set<ConstraintViolation<User>> violations = validator.validate(user1);
        assertEquals(0, violations.size());
    }

    @Test
    void checkBlankNameUserTest() {
        Set<ConstraintViolation<User>> violations = validator.validate(user2);
        assertEquals(0, violations.size());
    }

    @Test
    void checkWithoutLoginUserTest() {
        Set<ConstraintViolation<User>> violations = validator.validate(user3);
        assertEquals(1, violations.size());
    }

    @Test
    void checkAddFriendTest() {
        userController.addUser(user);
        userController.addUser(user1);
        userController.addUser(user2);
        userController.addUser(user5);
        userController.addUser(user6);
        userController.addUser(user7);
        userController.addFriend(user.getId(), user1.getId());
        userController.addFriend(user.getId(), user2.getId());
        userController.addFriend(user.getId(), user5.getId());
        userController.addFriend(user1.getId(), user2.getId());
        userController.addFriend(user1.getId(), user6.getId());
        List<User> commonFriends = userController.getCommonFriends(user.getId(), user1.getId());
        assertEquals(1, commonFriends.size());
        assertTrue(commonFriends.contains(user2));
        assertEquals(3, userController.getFriends(user.getId()).size());
    }
}



