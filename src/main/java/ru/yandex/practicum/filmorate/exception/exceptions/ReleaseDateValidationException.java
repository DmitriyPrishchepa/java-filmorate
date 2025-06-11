package ru.yandex.practicum.filmorate.exception.exceptions;

public class ReleaseDateValidationException extends RuntimeException {
    public ReleaseDateValidationException(String message) {
        super(message);
    }
}
