package ru.yandex.practicum.filmorate.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DateReleaseValidator implements ConstraintValidator<ReleaseDateValidation, LocalDate> {

    private LocalDate threshold;

    @Override
    public void initialize(ReleaseDateValidation constraintAnnotation) {
        this.threshold = LocalDate.parse(constraintAnnotation.value());
    }

    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext constraintValidatorContext) {
        return date.isAfter(threshold);
    }
}
