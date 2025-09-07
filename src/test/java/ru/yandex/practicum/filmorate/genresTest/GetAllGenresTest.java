package ru.yandex.practicum.filmorate.genresTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.BaseInstructions;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GetAllGenresTest extends BaseInstructions {

    @BeforeEach
    void cleanTables() {
        cleanAllTables();
    }

    @Test
    void getAll() {

        List<Genre> genres = genreService.getGenres();
        assertThat(genres).asList().size().isEqualTo(6);
    }
}
