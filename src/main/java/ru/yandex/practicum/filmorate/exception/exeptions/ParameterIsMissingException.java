package ru.yandex.practicum.filmorate.exception.exeptions;

public class ParameterIsMissingException extends RuntimeException {
    public ParameterIsMissingException(String message) {
        super(message);
    }
}
