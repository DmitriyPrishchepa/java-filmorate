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

    Film updateFilm(Film film);

    Optional<Film> getFilmById(Integer id);

    void likeFilm(Integer id, Integer userId);

    void unlikeFilm(Integer id, Integer userId);

    List<Film> getPopularFilms(Integer count);

    Integer getLikesOfFilm(Integer filmId);

    void clearLikes();

    void clearFilms();
}
