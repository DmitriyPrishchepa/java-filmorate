package ru.yandex.practicum.filmorate.validation;

import ru.yandex.practicum.filmorate.exception.exeptions.ElementNotFoundException;
import ru.yandex.practicum.filmorate.exception.exeptions.ValidateFilmException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

public class FilmValidator {

    public static void validateFilm(Film film) {
        if (film.getName().isBlank()) {
            throw new ValidateFilmException("Name cannot be blank");
        }

        if (film.getDescription().length() > 200) {
            throw new ValidateFilmException("Description cannot be more than 200 letters");
        }

        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidateFilmException("Date could not be in future");
        }

        if (film.getDuration() < 0) {
            throw new ValidateFilmException("Duration must be equals 0 or to be positive");
        }

        if (film.getMpa().getId() > 5) {
            throw new ElementNotFoundException("Incorrect mpa");
        }
    }
}
