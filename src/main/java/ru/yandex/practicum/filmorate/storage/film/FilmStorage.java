package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.List;

@Component
public interface FilmStorage {

    Collection<Film> getAllFilms();

    Film addFilm(Film film);

    Film updateFilm(Film film);

    Long getNexId();

    Film getFilmById(Long id);

    Long likeFilm(Long id, Long userId);

    Long unlikeFilm(Long id, Long userId);

    List<Film> getPopularFilms(Integer count);
}
