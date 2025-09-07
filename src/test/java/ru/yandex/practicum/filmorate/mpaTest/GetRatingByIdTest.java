package ru.yandex.practicum.filmorate.mpaTest;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.BaseInstructions;
import ru.yandex.practicum.filmorate.model.Mpa;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GetRatingByIdTest extends BaseInstructions {

    @BeforeEach
    void cleanTables() {
        cleanAllTables();
    }

    @Test
    void testGetRatingById() {

        Mpa rating = mpaService.getMpaById(1);

        assertThat(rating).hasFieldOrPropertyWithValue("name", "G");
    }
}
