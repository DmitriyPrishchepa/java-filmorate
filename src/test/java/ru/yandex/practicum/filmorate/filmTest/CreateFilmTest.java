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


//        filmDbStorage.addFilm(Film.builder()
//                .name("The Pursuit of Happyness")
//                .description("The Pursuit of Happyness")
//                .releaseDate(LocalDate.of(2003, 7, 15))
//                .duration(141)
//                .mpa(Mpa.builder()
//                        .id(3)
//                        .name("PG-13")
//                        .build())
//                .build());
//
//        filmDbStorage.addFilm(Film.builder()
//                .name("Bad boys 2")
//                .description("Bad boys 2")
//                .releaseDate(LocalDate.of(2006, 12, 9))
//                .duration(141)
//                .mpa(Mpa.builder()
//                        .id(3)
//                        .name("PG-13")
//                        .build())
//                .build());

        assertThat(testFilm).hasFieldOrPropertyWithValue("id", 2);
    }
}