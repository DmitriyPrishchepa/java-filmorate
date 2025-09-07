package ru.yandex.practicum.filmorate.filmTest;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.BaseInstructions;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GetAllFilmsTest extends BaseInstructions {


    @Test
    public void getAll() {

        createFilmForTest(
                "It",
                "It will come",
                LocalDate.of(2017, 9, 5),
                135,
                Mpa.builder()
                        .id(3)
                        .name("PG-13")
                        .build());

        Film testFilm = createFilmForTest(
                "Bad boys",
                "Bad boys, bad boys, what you gonna do...",
                LocalDate.of(1995, 4, 6),
                119,
                Mpa.builder()
                        .id(3)
                        .name("PG-13")
                        .build());

        createFilmForTest(
                "The Secret Life of Walter Mitty",
                "The Secret Life of Walter Mitty",
                LocalDate.of(2013, 10, 5),
                151,
                Mpa.builder()
                        .id(3)
                        .name("PG-13")
                        .build());

        List<Film> films = filmService.getAllFilms().stream().toList();
        assertThat(films).asList().size().isEqualTo(3);
    }
}