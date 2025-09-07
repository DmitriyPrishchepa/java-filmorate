package ru.yandex.practicum.filmorate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
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
        // Отключаем проверки внешних ключей (для H2), чтобы удалять таблицы в любом порядке
        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY FALSE");

        // Таблицы, которые не очищаем
        List<String> skipTables = List.of("genres", "mpa");

        // Получаем список всех таблиц из метаданных
        List<String> tables = jdbcTemplate.queryForList(
                "SELECT table_name FROM information_schema.tables " +
                        "WHERE table_schema='PUBLIC'", String.class);

        // Удаляем все данные из таблиц, кроме исключённых
        tables.stream()
                .filter(t -> !skipTables.contains(t.toLowerCase()))
                .forEach(t -> jdbcTemplate.execute("TRUNCATE TABLE " + t));

        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY TRUE"); // Включаем проверки обратно
    }

    protected Film createFilmForTest(
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

        return filmService.addFilm(film);
    }
}
