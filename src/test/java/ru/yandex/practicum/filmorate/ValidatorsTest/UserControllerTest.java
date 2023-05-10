package ru.yandex.practicum.filmorate.ValidatorsTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserControllerTest {
    private Validator validator;
    private User user;
    private User user1;
    private User user2;
    private User user3;
    private User user4;

    @BeforeEach
    void createUser() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
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
        user2.setLogin("LegionCommander");
        user2.setName(" ");
        user2.setBirthday(LocalDate.of(1200, 1, 1));

        user3 = new User();
        user3.setId(4);
        user3.setEmail("withoutLogin@yandex.ru");
        user3.setName("Puck");
        user3.setBirthday(LocalDate.of(1200, 1, 1));

        user4 = new User();
        user4.setId(4);
        user4.setEmail("loginWithBlank@yandex.ru");
        user4.setLogin("Legion Commander");
        user4.setName("Puck");
        user4.setBirthday(LocalDate.of(1200, 1, 1));
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
    void checkLoginWithBlankUserTest() {
        Set<ConstraintViolation<User>> violations = validator.validate(user4);
        assertEquals(1, violations.size());
    }
}



