package ru.yandex.practicum.filmorate;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.util.FilmsLikesComparator;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class FilmorateFilmsTests {
    private Validator validator;
    String messageException;
    Film testFilm;
    FilmsLikesComparator comparator = new FilmsLikesComparator();
    FilmStorage filmStorage = new InMemoryFilmStorage(comparator);
    FilmService filmService = new FilmService(filmStorage);
    FilmController filmController = new FilmController(filmService);

    @BeforeEach
    public void beforeEach() {
        testFilm = Film.builder()
                .name("new film")
                .description("new description")
                .releaseDate(LocalDate.of(2022, 7, 27))
                .duration(100)
                .build();

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    public void readException() {
        Set<ConstraintViolation<Film>> violations = validator.validate(testFilm);
        for (ConstraintViolation<Film> viol : violations) {
            messageException = viol.getMessage();
        }
    }

    @Test
    public void shouldReturnListOfFilms() {
        filmController.addFilm(testFilm);
        System.out.println(filmController.findAllFilms());
        assertTrue(filmController.findAllFilms().contains(testFilm));
    }

    @Test
    public void shouldThrowExceptionIfNameIsEmptyThroughAnnotations() {
        testFilm.setName("");

        readException();

        messageException = "не должно быть пустым";

        assertEquals("не должно быть пустым", messageException);
    }

    @Test
    public void shouldThrowExceptionIfNameIsNullThroughAnnotations() {
        testFilm.setName(null);

        readException();

        messageException = "не должно быть пустым";

        assertEquals("не должно быть пустым", messageException);
    }

    @Test
    public void shouldThrowExceptionIfDescriptionMoreThan200Letters() {
        testFilm.setDescription("1960-е годы. После закрытия нью-йоркского ночного " +
                "клуба на ремонт вышибала Тони по прозвищу Болтун ищет подработку на" +
                " пару месяцев. Как раз в это время Дон Ширли — утонченный светский лев," +
                " богатый и талантливый чернокожий музыкант, исполняющий классическую музыку " +
                "— собирается в турне по южным штатам, где ещё сильны расистские убеждения " +
                "и царит сегрегация. Он нанимает Тони в качестве водителя, телохранителя и " +
                "человека, способного решать текущие проблемы. У этих двоих так мало общего," +
                " и эта поездка навсегда изменит жизнь обоих.");

        readException();

        messageException = "размер должен находиться в диапазоне от 0 до 200";

        assertEquals("размер должен находиться в диапазоне от 0 до 200", messageException);
    }

    @Test
    public void shouldThrowExceptionIfDurationIsNegative() {
        testFilm.setDuration(-1);
        readException();

        messageException = "должно быть больше 0";

        assertEquals("должно быть больше 0", messageException);
    }

    @Test
    public void shouldThrowExceptionIfReleaseDateIsIncorrect() {
        testFilm.setReleaseDate(LocalDate.of(1850, 12, 11));
        readException();

        messageException = "дата должна быть больше 1895-12-28";

        assertEquals("дата должна быть больше 1895-12-28", messageException);
    }
}