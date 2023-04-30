package ru.yandex.practicum.filmorate.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class ValidatorForReleaseDate implements ConstraintValidator<ValidReleaseDate, LocalDate> {
    private LocalDate minimumDate;
//совместил https://aboutbits.it/blog/2022-09-20-spring-boot-custom-validation-annotations
//и https://ru.stackoverflow.com/questions/1501226/java-Как-обозначить-аннотацией-что-дата-не-раньше-определённой
    @Override
    public void initialize(ValidReleaseDate constraintAnnotation) {
        minimumDate = LocalDate.parse(constraintAnnotation.value()); //инициализировали
    }
    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        return !value.isBefore(minimumDate);   //true, если указанная дата не раньше 28.12.1895
    }
}
