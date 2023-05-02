package ru.yandex.practicum.filmorate.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidatorForLogin implements ConstraintValidator<ValidUserLogin, String> {
    String login;

    @Override
    public void initialize(final ValidUserLogin constraintAnnotation) {
        this.login = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String login, ConstraintValidatorContext context) {
        if (login == null) {
            return false;
        }
        return (!(login.contains(" ")));
    }
}
