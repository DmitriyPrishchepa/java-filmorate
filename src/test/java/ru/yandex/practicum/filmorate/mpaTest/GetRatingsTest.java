package ru.yandex.practicum.filmorate.mpaTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.BaseInstructions;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GetRatingsTest extends BaseInstructions {

    @BeforeEach
    void cleanTables() {
        cleanAllTables();
    }

    @Test
    void testGetRatings() {

        List<Mpa> ratings = mpaService.getRatings();
        assertThat(ratings).asList().size().isEqualTo(5);
    }
}
