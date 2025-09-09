package ru.yandex.practicum.filmorate.filmTest;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.BaseInstructions;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
public class GetPopularTest extends BaseInstructions {

    @BeforeEach
    void cleanTables() {
        cleanAllTables();
    }

    @Test
    public void testGetPopularAndLikeFilm() {

        createUserForTest(
                "Svyatoslav",
                "svyat@gmail.com",
                "svyatoy",
                LocalDate.of(1992, 5, 11)
        );

        createUserForTest(
                "Oleg",
                "olli@gmail.com",
                "olegek",
                LocalDate.of(1991, 5, 11)
        );

        createUserForTest(
                "John",
                "jonny@gmail.com",
                "j",
                LocalDate.of(1991, 10, 15)
        );

        createFilmForTest(
                "It",
                "It will come",
                LocalDate.of(2017, 9, 5),
                135,
                Mpa.builder()
                        .id(3)
                        .name("PG-13")
                        .build());

        createFilmForTest(
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

//        createFilmForTest(
//                "The Pursuit of Happyness",
//                "The Pursuit of Happyness",
//                LocalDate.of(2003, 7, 15),
//                141,
//                Mpa.builder()
//                        .id(3)
//                        .name("PG-13")
//                        .build());
//
//        createFilmForTest(
//                "Bad boys 2",
//                "Bad boys 2",
//                LocalDate.of(2006, 12, 9),
//                141,
//                Mpa.builder()
//                        .id(3)
//                        .name("PG-13")
//                        .build());

        List<Film> allFilms = filmService.getAllFilms().stream().toList();

        for (Film film : allFilms) {
            log.debug("film {}", film);
        }

        Film film1 = filmService.getFilmById(1);
        Film film2 = filmService.getFilmById(2);
        Film film3 = filmService.getFilmById(3);

        User user1 = userService.getUserById(1);
        User user2 = userService.getUserById(2);
        User user3 = userService.getUserById(3);

        filmService.likeFilm(1, user1.getId());
        filmService.likeFilm(1, user2.getId());
        filmService.likeFilm(1, user3.getId());

        filmService.likeFilm(2, user1.getId());
        filmService.likeFilm(2, user2.getId());

        filmService.likeFilm(3, user1.getId());

        Integer likes1 = filmService.getLikesOfFilm(film1.getId());
        assertThat(likes1).isEqualTo(3);

        Integer likes2 = filmService.getLikesOfFilm(film2.getId());
        assertThat(likes2).isEqualTo(2);


        Integer likes3 = filmService.getLikesOfFilm(film3.getId());
        assertThat(likes3).isEqualTo(1);


        List<Film> popular = filmService.getPopularFilms(3);
        assertThat(popular).asList().size().isEqualTo(3);
    }
}