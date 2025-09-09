package ru.yandex.practicum.filmorate.filmTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.BaseInstructions;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GetPopularTest extends BaseInstructions {

    @BeforeEach
    void cleanTables() {
        cleanAllTables();
    }

    @Test
    public void testGetPopularAndLikeFilm() {

        addSomeUsersBatchUpdate(users);

        addSomeFilmsBatchUpdate(films);

        List<Film> allFilms = filmService.getAllFilms().stream().toList();
        List<User> allUsers = userService.getAllUsers().stream().toList();

        Film film1 = allFilms.getFirst();
        Film film2 = allFilms.get(1);
        Film film3 = allFilms.get(2);

        User user1 = allUsers.getFirst();
        User user2 = allUsers.get(1);
        User user3 = allUsers.getLast();

        filmService.likeFilm(film1.getId(), user1.getId());
        filmService.likeFilm(film1.getId(), user2.getId());
        filmService.likeFilm(film1.getId(), user3.getId());

        filmService.likeFilm(film2.getId(), user1.getId());
        filmService.likeFilm(film2.getId(), user2.getId());

        filmService.likeFilm(film3.getId(), user1.getId());

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