package ru.yandex.practicum.filmorate.exception.exeptions;

import jakarta.validation.ConstraintViolationException;

import java.util.Set;

public class ReleaseDateValidationException extends ConstraintViolationException {
    public ReleaseDateValidationException(String message) {
        super(Set.of(message));
    }
}
