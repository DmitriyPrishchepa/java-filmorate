package ru.yandex.practicum.filmorate.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dto.dtos.FilmDto;
import ru.yandex.practicum.filmorate.dto.requests.films_requests.NewFilmRequest;
import ru.yandex.practicum.filmorate.dto.requests.films_requests.UpdateFilmRequest;
import ru.yandex.practicum.filmorate.model.Film;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FilmMapper {

    public static FilmDto mapFilmToDto(Film film) {
        return FilmDto.builder()
                .id(film.getId())
                .name(film.getName())
                .description(film.getDescription())
                .releaseDate(film.getReleaseDate())
                .duration(film.getDuration())
                .mpaId(film.getMpaId())
                .build();
    }

    public static Film mapToFilm(NewFilmRequest request) {
        return Film.builder()
                .name(request.getName())
                .description(request.getDescription())
                .releaseDate(request.getReleaseDate())
                .duration(request.getDuration())
                .mpaId(request.getMpaId())
                .genres(request.getGenres())
                .build();
    }

    public static Film updateFilmFields(Film film, UpdateFilmRequest request) {
        if (request.hasName()) {
            film.setName(request.getName());
        }
        if (request.hasDescription()) {
            film.setDescription(request.getDescription());
        }
        if (request.hasReleaseDate()) {
            film.setReleaseDate(request.getReleaseDate());
        }
        if (request.hasDuration()) {
            film.setDuration(request.getDuration());
        }
        if (request.hasMpaId()) {
            film.setMpaId(request.getMpaId());
        }
        if (request.hasGenres()) {
            film.setGenres(request.getGenres());
        }
        return film;
    }
}
