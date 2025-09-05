package ru.yandex.practicum.filmorate.genresTest;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.genres.GenresDbStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GetGenresOfFilmTest {

    private final GenresDbStorage genresDbStorage;
    private final FilmDbStorage filmDbStorage;

    @Test
    void getGenresOfFilm() {
        Film film = Film.builder()
                .name("It")
                .description("It will come")
                .releaseDate(LocalDate.of(2017, 9, 5))
                .duration(135)
                .mpa(Mpa.builder()
                        .id(3)
                        .name("PG-13")
                        .build())
                .build();

        List<Genre> genres = new ArrayList<>();

        genres.add(Genre.builder()
                .id(1)
                .name("Комедия")
                .build());

        genres.add(Genre.builder()
                .id(2)
                .name("Драма")
                .build());

        film.getGenres().addAll(genres);

        filmDbStorage.addFilm(film);

        assertThat(film).hasFieldOrPropertyWithValue("id", 1);

        Optional<Film> filmOptional = filmDbStorage.getFilmById(1);

        if (filmOptional.isPresent()) {
            List<Genre> genreOptional = genresDbStorage.getGenresOfFilm(filmOptional.get().getId());
            assertThat(genreOptional).asList().size().isEqualTo(2);
        }

        genresDbStorage.clearGenres();
        filmDbStorage.clearFilms();
    }
}
