package ru.yandex.practicum.filmorate.filmTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.BaseInstructions;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UpdateFilmTest extends BaseInstructions {

    @BeforeEach
    void cleanTables() {
        cleanAllTables();
    }


    @Test
    public void testUpdateFilm() {
        createFilmForTest(
                "It",
                "It will come",
                LocalDate.of(2017, 9, 5),
                135,
                Mpa.builder()
                        .id(3)
                        .name("PG-13")
                        .build());

        Film newFilm = Film.builder()
                .id(1)
                .name("Pirates of Caribbean")
                .description("It will come")
                .releaseDate(LocalDate.of(2017, 9, 5))
                .duration(135)
                .mpa(Mpa.builder()
                        .id(3)
                        .name("PG-13")
                        .build())
                .build();

        Film updatedFilm = filmService.updateFilm(newFilm);

        assertThat(updatedFilm).hasFieldOrPropertyWithValue("name", "Pirates of Caribbean");
    }
}