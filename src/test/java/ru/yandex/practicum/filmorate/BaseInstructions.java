package ru.yandex.practicum.filmorate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.*;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@AutoConfigureTestDatabase
public class BaseInstructions {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @Autowired
    protected FilmService filmService;

    @Autowired
    protected UserService userService;

    @Autowired
    protected FriendShipService friendShipService;

    @Autowired
    protected MpaService mpaService;

    @Autowired
    protected GenreService genreService;

    protected void cleanAllTables() {
        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY FALSE");

        List<String> skipTables = List.of("genres", "mpa");

        List<String> tables = jdbcTemplate.queryForList(
                "SELECT table_name FROM information_schema.tables " +
                        "WHERE table_schema='PUBLIC'", String.class);

        tables.stream()
                .filter(t -> !skipTables.contains(t.toLowerCase()))
                .forEach(t -> jdbcTemplate.execute("TRUNCATE TABLE " + t));


        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY TRUE");
    }

    protected List<Genre> genres = List.of(
            Genre.builder()
                    .id(1)
                    .name("Комедия")
                    .build(),
            Genre.builder()
                    .id(2)
                    .name("Драма")
                    .build(),
            Genre.builder()
                    .id(3)
                    .name("Мультфильм")
                    .build(),
            Genre.builder()
                    .id(4)
                    .name("Триллер")
                    .build(),
            Genre.builder()
                    .id(5)
                    .name("Документальный")
                    .build(),
            Genre.builder()
                    .id(6)
                    .name("Боевик")
                    .build()
    );


    protected void createFilmForTest(
            String name,
            String description,
            LocalDate releaseDate,
            Integer duration,
            Mpa mpa
    ) {
        Film film = Film.builder()
                .name(name)
                .description(description)
                .releaseDate(releaseDate)
                .duration(duration)
                .mpa(mpa)
                .build();

        List<Genre> genres = List.of(
                Genre.builder()
                        .id(1)
                        .name("Комедия")
                        .build(),
                Genre.builder()
                        .id(2)
                        .name("Драма")
                        .build()
        );

        film.getGenres().addAll(genres);

        filmService.addFilm(film);
    }

    protected void updateFilmForTest(Film newFilm) {

        Film film = Film.builder()
                .name(newFilm.getName())
                .description(newFilm.getDescription())
                .releaseDate(newFilm.getReleaseDate())
                .duration(newFilm.getDuration())
                .mpa(newFilm.getMpa())
                .build();

        List<Genre> genres = List.of(
                Genre.builder()
                        .id(1)
                        .name("Комедия")
                        .build(),
                Genre.builder()
                        .id(2)
                        .name("Драма")
                        .build()
        );

        film.getGenres().addAll(genres);

        filmService.updateFilm(newFilm);
    }

    protected void createUserForTest(
            String name,
            String email,
            String login,
            LocalDate birthday
    ) {
        User user = User.builder()
                .name(name)
                .email(email)
                .login(login)
                .birthday(birthday)
                .build();

        userService.addUser(user);
    }

    protected void updateUserForTest(User newUser) {

        User user = User.builder()
                .name(newUser.getName())
                .email(newUser.getEmail())
                .login(newUser.getLogin())
                .birthday(newUser.getBirthday())
                .build();

        userService.updateUser(user)
    }
}
