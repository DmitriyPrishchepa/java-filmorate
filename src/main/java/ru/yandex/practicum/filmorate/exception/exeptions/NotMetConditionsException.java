package ru.yandex.practicum.filmorate.exception.exeptions;

public class NotMetConditionsException extends RuntimeException {
    public NotMetConditionsException(String message) {
        super(message);
    }
}
