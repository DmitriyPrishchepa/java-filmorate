package ru.yandex.practicum.filmorate.validation;

import jakarta.validation.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.time.LocalDate;
import java.util.ArrayList;

public class FilmValidator {
    public static Film validateFilm(Film film) {
        if (film.getName().isBlank()) {
            throw new ValidationException("Name cannot be blank");
        }

        if (film.getDescription().length() > 200) {
            throw new ValidationException("Description cannot be more than 200 letters");
        }

        if (film.getReleaseDate().isAfter(LocalDate.now())) {
            throw new ValidationException("Date could not be in future");
        }

        if (film.getGenres() == null) {
            ArrayList<Genre> newGenres = new ArrayList<>();
            film.getGenres().addAll(newGenres);
        }

        return film;
    }
}
