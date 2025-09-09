package ru.yandex.practicum.filmorate.util;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;

@Component
public class FilmUpdater {
    public static Film updateFieldsOfFilm(Film film, Film request) {
        if (request.getName() != null && !request.getName().isBlank()) {
            film.setName(request.getName());
        }

        if (request.getDescription() != null) {
            film.setDescription(request.getDescription());
        }

        if (request.getReleaseDate() != null) {
            film.setReleaseDate(request.getReleaseDate());
        }

        if (request.getDuration() != null) {
            film.setDuration(request.getDuration());
        }

        if (request.getMpa().getId() != null) {
            film.setMpa(
                    Mpa.builder()
                            .id(request.getMpa().getId())
                            .name(request.getMpa().getName())
                            .build());
        }

        if (request.getGenres() != null) {
            film.getGenres().clear();
            request.getGenres().addAll(request.getGenres());
        }

        return film;
    }
}
