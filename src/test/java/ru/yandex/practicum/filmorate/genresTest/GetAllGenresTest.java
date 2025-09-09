package ru.yandex.practicum.filmorate.genresTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import ru.yandex.practicum.filmorate.BaseInstructions;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GetAllGenresTest extends BaseInstructions {

    @BeforeEach
    void cleanTables() {
        cleanAllTables();
    }

    public void batchUpdate(final List<Genre> listOfGenres) {

        jdbcTemplate.update("DELETE FROM genres");

        final String INSERT_INTO_FILM_GENRES = "INSERT INTO genres(id, name) VALUES(?, ?)";
        jdbcTemplate.batchUpdate(
                INSERT_INTO_FILM_GENRES,
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setInt(1, listOfGenres.get(i).getId());
                        ps.setString(2, listOfGenres.get(i).getName());
                    }

                    @Override
                    public int getBatchSize() {
                        return genres.size();
                    }
                });
    }

    @Test
    void getAll() {

        batchUpdate(genres);

        List<Genre> genresFromDB = genreService.getGenres();
        assertThat(genresFromDB).asList().size().isEqualTo(6);
    }
}
