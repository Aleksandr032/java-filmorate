package ru.yandex.practicum.filmorate.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidatorForLogin implements ConstraintValidator<ValidUserLogin, String> {
    String login;
    boolean valid;

    @Override
    public void initialize(final ValidUserLogin constraintAnnotation) {
        this.login = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String login, ConstraintValidatorContext context) {
        return !(login.contains(" "));
    }
}
