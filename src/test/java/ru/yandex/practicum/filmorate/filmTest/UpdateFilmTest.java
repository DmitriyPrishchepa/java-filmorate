package ru.yandex.practicum.filmorate.filmTest;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.BaseInstructions;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.time.LocalDate;
import java.util.List;

@Slf4j
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

        List<Film> allFilms = filmService.getAllFilms().stream().toList();

        log.debug("size {}", allFilms.size());

        Film filmFromDb = allFilms.getFirst();

        log.debug("first {}", filmFromDb);

        filmFromDb.setName("Pir of car");

        updateFilmForTest(filmFromDb);

        Film newFilmFromDb = filmService.getAllFilms().stream().toList().getFirst();

        log.debug("new {}", newFilmFromDb);
    }
}