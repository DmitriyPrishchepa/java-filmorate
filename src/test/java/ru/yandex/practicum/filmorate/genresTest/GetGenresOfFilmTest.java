package ru.yandex.practicum.filmorate.genresTest;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.BaseInstructions;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GetGenresOfFilmTest extends BaseInstructions {

    @Test
    void getGenresOfFilm() {

        createFilmForTest(
                "It",
                "It will come",
                LocalDate.of(2017, 9, 5),
                135,
                Mpa.builder()
                        .id(3)
                        .name("PG-13")
                        .build());

        List<Film> films = filmService.getAllFilms().stream().toList();
        Film film1 = films.getFirst();

        List<Genre> genreOptional = genreService.getGenresOfFilm(film1.getId());
        assertThat(genreOptional).asList().size().isEqualTo(2);
    }
}
