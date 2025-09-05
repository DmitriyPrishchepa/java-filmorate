package ru.yandex.practicum.filmorate.filmTest;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GetPopularTest {

    private final FilmDbStorage filmDbStorage;
    private final UserDbStorage userDbStorage;

    @Test
    public void testGetPopularAndLikeFilm() {

        userDbStorage.addUser(User.builder()
                .name("Svyatoslav")
                .email("svyat@gmail.com")
                .login("svyatoy")
                .birthday(LocalDate.of(1992, 5, 11))
                .build());

        userDbStorage.addUser(User.builder()
                .name("Oleg")
                .email("olli@gmail.com")
                .login("olegek")
                .birthday(LocalDate.of(1991, 5, 11))
                .build());

        userDbStorage.addUser(User.builder()
                .name("John")
                .email("jonny@gmail.com")
                .login("j")
                .birthday(LocalDate.of(1991, 10, 15))
                .build());

        filmDbStorage.addFilm(Film.builder()
                .name("It")
                .description("It will come")
                .releaseDate(LocalDate.of(2017, 9, 5))
                .duration(135)
                .mpa(Mpa.builder()
                        .id(3)
                        .name("PG-13")
                        .build())
                .build());

        filmDbStorage.addFilm(Film.builder()
                .name("Bad boys")
                .description("Bad boys, bad boys, what you gonna do...")
                .releaseDate(LocalDate.of(1995, 4, 6))
                .duration(119)
                .mpa(Mpa.builder()
                        .id(3)
                        .name("PG-13")
                        .build())
                .build());

        Film film = filmDbStorage.addFilm(Film.builder()
                .name("The Secret Life of Walter Mitty")
                .description("The Secret Life of Walter Mitty")
                .releaseDate(LocalDate.of(2013, 10, 5))
                .duration(151)
                .mpa(Mpa.builder()
                        .id(3)
                        .name("PG-13")
                        .build())
                .build());

        filmDbStorage.addFilm(Film.builder()
                .name("The Pursuit of Happyness")
                .description("The Pursuit of Happyness")
                .releaseDate(LocalDate.of(2003, 7, 15))
                .duration(141)
                .mpa(Mpa.builder()
                        .id(3)
                        .name("PG-13")
                        .build())
                .build());

        filmDbStorage.addFilm(Film.builder()
                .name("Bad boys 2")
                .description("Bad boys 2")
                .releaseDate(LocalDate.of(2006, 12, 9))
                .duration(141)
                .mpa(Mpa.builder()
                        .id(3)
                        .name("PG-13")
                        .build())
                .build());

        Optional<Film> film1Optional = filmDbStorage.getFilmById(1);
        Optional<Film> film2Optional = filmDbStorage.getFilmById(2);
        Optional<Film> film3Optional = filmDbStorage.getFilmById(3);

        Optional<User> user1Optional = userDbStorage.getUserById(1);
        Optional<User> user2Optional = userDbStorage.getUserById(2);
        Optional<User> user3Optional = userDbStorage.getUserById(3);

        if (user1Optional.isPresent() && user2Optional.isPresent() && user3Optional.isPresent()) {
            filmDbStorage.likeFilm(1, user1Optional.get().getId());
            filmDbStorage.likeFilm(1, user2Optional.get().getId());
            filmDbStorage.likeFilm(1, user3Optional.get().getId());

            filmDbStorage.likeFilm(2, user1Optional.get().getId());
            filmDbStorage.likeFilm(2, user2Optional.get().getId());

            filmDbStorage.likeFilm(3, user1Optional.get().getId());

            if (film1Optional.isPresent()) {
                Integer likes1 = filmDbStorage.getLikesOfFilm(film1Optional.get().getId());
                assertThat(likes1).isEqualTo(3);
            }

            if (film2Optional.isPresent()) {
                Integer likes2 = filmDbStorage.getLikesOfFilm(film2Optional.get().getId());
                assertThat(likes2).isEqualTo(2);
            }

            if (film3Optional.isPresent()) {
                Integer likes3 = filmDbStorage.getLikesOfFilm(film3Optional.get().getId());
                assertThat(likes3).isEqualTo(1);
            }

            List<Film> popular = filmDbStorage.getPopularFilms(3);
            assertThat(popular).asList().size().isEqualTo(3);

            filmDbStorage.clearLikes();
            filmDbStorage.clearFilms();
        }
    }
}
