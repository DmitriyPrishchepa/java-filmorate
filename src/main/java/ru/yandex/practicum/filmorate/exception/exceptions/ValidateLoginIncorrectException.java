package ru.yandex.practicum.filmorate.exception.exceptions;

public class ValidateLoginIncorrectException extends RuntimeException {
    public ValidateLoginIncorrectException(String message) {
        super(message);
    }
}
