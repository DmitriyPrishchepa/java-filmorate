package ru.yandex.practicum.filmorate.filmTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.BaseInstructions;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GetPopularTest extends BaseInstructions {

    @BeforeEach
    void cleanTables() {
        cleanAllTables();
    }

    @Test
    public void testGetPopularAndLikeFilm() {

        userService.addUser(User.builder()
                .name("Svyatoslav")
                .email("svyat@gmail.com")
                .login("svyatoy")
                .birthday(LocalDate.of(1992, 5, 11))
                .build());

        userService.addUser(User.builder()
                .name("Oleg")
                .email("olli@gmail.com")
                .login("olegek")
                .birthday(LocalDate.of(1991, 5, 11))
                .build());

        userService.addUser(User.builder()
                .name("John")
                .email("jonny@gmail.com")
                .login("j")
                .birthday(LocalDate.of(1991, 10, 15))
                .build());

        filmService.addFilm(Film.builder()
                .name("It")
                .description("It will come")
                .releaseDate(LocalDate.of(2017, 9, 5))
                .duration(135)
                .mpa(Mpa.builder()
                        .id(3)
                        .name("PG-13")
                        .build())
                .build());

        filmService.addFilm(Film.builder()
                .name("Bad boys")
                .description("Bad boys, bad boys, what you gonna do...")
                .releaseDate(LocalDate.of(1995, 4, 6))
                .duration(119)
                .mpa(Mpa.builder()
                        .id(3)
                        .name("PG-13")
                        .build())
                .build());

        Film film = filmService.addFilm(Film.builder()
                .name("The Secret Life of Walter Mitty")
                .description("The Secret Life of Walter Mitty")
                .releaseDate(LocalDate.of(2013, 10, 5))
                .duration(151)
                .mpa(Mpa.builder()
                        .id(3)
                        .name("PG-13")
                        .build())
                .build());

        filmService.addFilm(Film.builder()
                .name("The Pursuit of Happyness")
                .description("The Pursuit of Happyness")
                .releaseDate(LocalDate.of(2003, 7, 15))
                .duration(141)
                .mpa(Mpa.builder()
                        .id(3)
                        .name("PG-13")
                        .build())
                .build());

        filmService.addFilm(Film.builder()
                .name("Bad boys 2")
                .description("Bad boys 2")
                .releaseDate(LocalDate.of(2006, 12, 9))
                .duration(141)
                .mpa(Mpa.builder()
                        .id(3)
                        .name("PG-13")
                        .build())
                .build());

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