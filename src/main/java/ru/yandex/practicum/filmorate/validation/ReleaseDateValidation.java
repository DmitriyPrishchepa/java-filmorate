package ru.yandex.practicum.filmorate.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = DateReleaseValidator.class)
@Documented
public @interface ReleaseDateValidation {
    String value();

    String message() default "Date must be after {value}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
