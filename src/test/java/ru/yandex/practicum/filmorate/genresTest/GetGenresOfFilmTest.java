package ru.yandex.practicum.filmorate.genresTest;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.BaseInstructions;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GetGenresOfFilmTest extends BaseInstructions {

    @Test
    void getGenresOfFilm() {

        Film film = Film.builder()
                .name("It")
                .description("It will come")
                .releaseDate(LocalDate.of(2017, 9, 5))
                .duration(135)
                .mpa(Mpa.builder()
                        .id(3)
                        .name("PG-13")
                        .build())
                .build();

        List<Genre> genres = new ArrayList<>();

        genres.add(Genre.builder()
                .id(1)
                .name("Комедия")
                .build());

        genres.add(Genre.builder()
                .id(2)
                .name("Драма")
                .build());

        film.getGenres().addAll(genres);

        filmService.addFilm(film);

        assertThat(film).hasFieldOrPropertyWithValue("id", 1);

        Film film1 = filmService.getFilmById(1);

        List<Genre> genreOptional = genreService.getGenresOfFilm(film1.getId());
        assertThat(genreOptional).asList().size().isEqualTo(2);
    }
}
