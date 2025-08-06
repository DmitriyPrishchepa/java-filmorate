package ru.yandex.practicum.filmorate.exception.exeptions;

public class InternalServerException extends RuntimeException {
    public InternalServerException(String message) {
        super(message);
    }
}
