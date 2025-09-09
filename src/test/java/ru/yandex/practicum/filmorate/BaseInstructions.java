package ru.yandex.practicum.filmorate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.*;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
        userService.updateUser(newUser);
    }

    protected List<User> users = List.of(
            User.builder()
                    .name("Alex")
                    .email("alex@gmail.com")
                    .login("alexandr")
                    .birthday(LocalDate.of(1995, 10, 10))
                    .build(),
            User.builder()
                    .name("Dima")
                    .email("dimchik@gmail.com")
                    .login("dmitr")
                    .birthday(LocalDate.of(2000, 10, 10))
                    .build(),
            User.builder()
                    .name("Igor")
                    .email("igor@gmail.com")
                    .login("iggir")
                    .birthday(LocalDate.of(1999, 12, 5))
                    .build());

    protected List<Film> films = List.of(
            Film.builder()
                    .name("It")
                    .description("It will come")
                    .releaseDate(LocalDate.of(2017, 9, 5))
                    .duration(135)
                    .mpa(Mpa.builder()
                            .id(3)
                            .name("PG-13")
                            .build())
                    .build(),
            Film.builder()
                    .name("Bad boys")
                    .description("Bad boys, bad boys, what you gonna do...")
                    .releaseDate(LocalDate.of(1995, 4, 6))
                    .duration(119)
                    .mpa(Mpa.builder()
                            .id(3)
                            .name("PG-13")
                            .build())
                    .build(),
            Film.builder()
                    .name("The Secret Life of Walter Mitty")
                    .description("The Secret Life of Walter Mitty")
                    .releaseDate(LocalDate.of(2013, 10, 5))
                    .duration(151)
                    .mpa(Mpa.builder()
                            .id(3)
                            .name("PG-13")
                            .build())
                    .build()
    );


    protected void addSomeUsersBatchUpdate(List<User> usersList) {
        final String INSERT_INTO_USERS = "INSERT INTO users(name, email, login, birthday) " +
                "VALUES (?, ?, ?, ?)";
        jdbcTemplate.batchUpdate(
                INSERT_INTO_USERS,
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, usersList.get(i).getName());
                        ps.setString(2, usersList.get(i).getEmail());
                        ps.setString(3, usersList.get(i).getLogin());
                        ps.setDate(4, Date.valueOf(usersList.get(i).getBirthday()));
                    }

                    @Override
                    public int getBatchSize() {
                        return users.size();
                    }
                });
    }

    protected void addSomeFilmsBatchUpdate(List<Film> filmsList) {
        final String INSERT_INTO_FILMS = "INSERT INTO films(name, description, release_date, duration, mpa_id) " +
                "VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.batchUpdate(
                INSERT_INTO_FILMS,
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, filmsList.get(i).getName());
                        ps.setString(2, filmsList.get(i).getDescription());
                        ps.setDate(3, Date.valueOf(filmsList.get(i).getReleaseDate()));
                        ps.setInt(4, filmsList.get(i).getDuration());
                        ps.setInt(5, filmsList.get(i).getMpa().getId());
                    }

                    @Override
                    public int getBatchSize() {
                        return filmsList.size();
                    }
                });
    }
}
