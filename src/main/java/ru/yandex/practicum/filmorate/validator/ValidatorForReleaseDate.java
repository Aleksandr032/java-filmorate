package ru.yandex.practicum.filmorate.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class ValidatorForReleaseDate implements ConstraintValidator<ValidReleaseDate, LocalDate> {
    private LocalDate minimumDate;

    @Override
    public void initialize(ValidReleaseDate constraintAnnotation) {
        minimumDate = LocalDate.parse(constraintAnnotation.value()); //инициализировали
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        return !value.isBefore(LocalDate.of(1895, 12, 28));   //true, если указанная дата не раньше 28.12.1895
    }
}
