package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.mpa.MpaDbStorage;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MpaTests {

    private final MpaDbStorage mpaDbStorage;

    @Test
    void testGetRatings() {
        List<Mpa> ratings = mpaDbStorage.getRatings();
        assertThat(ratings).asList().size().isEqualTo(5);
    }

    @Test
    void testGetRatingById() {
        Optional<Mpa> ratingOptional = mpaDbStorage.getRatingById(1);

        assertThat(ratingOptional)
                .isPresent()
                .hasValueSatisfying(mpa -> {
                    assertThat(mpa).hasFieldOrPropertyWithValue("name", "G");
                });
    }
}
