package ru.yandex.practicum.filmorate.exception.exeptions;

public class ConditionsNotMetException extends RuntimeException {
  public ConditionsNotMetException(String message) {
    super(message);
  }
}
