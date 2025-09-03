package ru.yandex.practicum.filmorate.filmTest;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GetAllFilmsTest {

    private final FilmDbStorage filmDbStorage;

    @Test
    public void getAll() {

        filmDbStorage.addFilm(Film.builder()
                .name("It")
                .description("It will come")
                .releaseDate(LocalDate.of(2017, 9, 5))
                .duration(135)
                .mpa(Mpa.builder()
                        .id(3)
                        .name("PG-13")
                        .build())
                .build());

        filmDbStorage.addFilm(Film.builder()
                .name("Bad boys")
                .description("Bad boys, bad boys, what you gonna do...")
                .releaseDate(LocalDate.of(1995, 4, 6))
                .duration(119)
                .mpa(Mpa.builder()
                        .id(3)
                        .name("PG-13")
                        .build())
                .build());

        Film film = filmDbStorage.addFilm(Film.builder()
                .name("The Secret Life of Walter Mitty")
                .description("The Secret Life of Walter Mitty")
                .releaseDate(LocalDate.of(2013, 10, 5))
                .duration(151)
                .mpa(Mpa.builder()
                        .id(3)
                        .name("PG-13")
                        .build())
                .build());

        filmDbStorage.addFilm(Film.builder()
                .name("The Pursuit of Happyness")
                .description("The Pursuit of Happyness")
                .releaseDate(LocalDate.of(2003, 7, 15))
                .duration(141)
                .mpa(Mpa.builder()
                        .id(3)
                        .name("PG-13")
                        .build())
                .build());

        filmDbStorage.addFilm(Film.builder()
                .name("Bad boys 2")
                .description("Bad boys 2")
                .releaseDate(LocalDate.of(2006, 12, 9))
                .duration(141)
                .mpa(Mpa.builder()
                        .id(3)
                        .name("PG-13")
                        .build())
                .build());

        List<Film> films = filmDbStorage.getAllFilms().stream().toList();
        assertThat(films).asList().size().isEqualTo(5);
    }
}
