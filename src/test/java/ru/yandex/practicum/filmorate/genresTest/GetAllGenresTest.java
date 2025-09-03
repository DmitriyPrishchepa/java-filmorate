package ru.yandex.practicum.filmorate.genresTest;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genres.GenresDbStorage;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GetAllGenresTest {

    private final GenresDbStorage genresDbStorage;

    @Test
    void getAll() {
        List<Genre> genres = genresDbStorage.getGenres();
        assertThat(genres).asList().size().isEqualTo(6);
    }
}
