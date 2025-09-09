package ru.yandex.practicum.filmorate.genresTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.BaseInstructions;
import ru.yandex.practicum.filmorate.model.Genre;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GetGenreByIdTest extends BaseInstructions {

    @BeforeEach
    void cleanTables() {
        cleanAllTables();
    }

    @Test
    void getById() {

        Genre genre = genreService.getGenreById(3);

        assertThat(genre).hasFieldOrPropertyWithValue("name", "Мультфильм");
    }
}
