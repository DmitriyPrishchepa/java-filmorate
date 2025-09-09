package ru.yandex.practicum.filmorate.filmTest;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import ru.yandex.practicum.filmorate.BaseInstructions;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
public class UpdateFilmTest extends BaseInstructions {

    @BeforeEach
    void cleanTables() {
        cleanAllTables();
    }

    public void batchUpdate(final Film film) {
        final String INSERT_INTO_FILM_GENRES = "INSERT INTO film_genres (film_id, genre_id) VALUES (?, ?)";
        jdbcTemplate.batchUpdate(
                INSERT_INTO_FILM_GENRES,
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setInt(1, film.getId());
                        ps.setInt(2, film.getGenres().get(i).getId());
                    }

                    @Override
                    public int getBatchSize() {
                        return film.getGenres().size();
                    }
                });
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

        Film film = filmService.getFilmById(1);

        film.setName("Pirates of caribbean");

        Film newUpdatedFilm = filmService.updateFilm(film);
        assertThat(newUpdatedFilm).hasFieldOrPropertyWithValue("name", "Pirates of caribbean");
    }
}