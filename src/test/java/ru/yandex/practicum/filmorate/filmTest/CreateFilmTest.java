package ru.yandex.practicum.filmorate.filmTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.BaseInstructions;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CreateFilmTest extends BaseInstructions {

    @BeforeEach
    void cleanTables() {
        cleanAllTables();
    }

    @Test
    public void testCreateFilm() {

        Film film = createFilmForTest(
                "It",
                "It will come",
                LocalDate.of(2017, 9, 5),
                135,
                Mpa.builder()
                        .id(3)
                        .name("PG-13")
                        .build());

//        Film testFilm = createFilmForTest(
//                "Bad boys",
//                "Bad boys, bad boys, what you gonna do...",
//                LocalDate.of(1995, 4, 6),
//                119,
//                Mpa.builder()
//                        .id(3)
//                        .name("PG-13")
//                        .build());
//
//        createFilmForTest(
//                "The Secret Life of Walter Mitty",
//                "The Secret Life of Walter Mitty",
//                LocalDate.of(2013, 10, 5),
//                151,
//                Mpa.builder()
//                        .id(3)
//                        .name("PG-13")
//                        .build());

        assertThat(film).hasFieldOrPropertyWithValue("id", 1);
    }
}