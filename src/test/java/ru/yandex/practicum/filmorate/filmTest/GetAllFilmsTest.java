package ru.yandex.practicum.filmorate.filmTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.BaseInstructions;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GetAllFilmsTest extends BaseInstructions {

    @BeforeEach
    void cleanTables() {
        cleanAllTables();
    }

    @Test
    public void getAll() {

        addSomeFilmsBatchUpdate(films);

        List<Film> films = filmService.getAllFilms().stream().toList();

        assertThat(films).asList().size().isEqualTo(3);
    }
}