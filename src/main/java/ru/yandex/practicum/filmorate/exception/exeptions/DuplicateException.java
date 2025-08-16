package ru.yandex.practicum.filmorate.exception.exeptions;

public class DuplicateException extends RuntimeException {
    public DuplicateException(String message) {
        super(message);
    }
}
