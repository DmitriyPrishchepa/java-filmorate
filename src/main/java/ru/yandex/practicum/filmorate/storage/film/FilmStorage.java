package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
public interface FilmStorage {

    Collection<Film> getAllFilms();

    Film addFilm(Film film);

    Optional<Film> updateFilm(Film film);

    Long getNexId();

    Film getFilmById(Long id);

    Film likeFilm(Long id, Long userId);

    Film unlikeFilm(Long id, Long userId);

    List<Film> getPopularFilms(Integer count);
}
