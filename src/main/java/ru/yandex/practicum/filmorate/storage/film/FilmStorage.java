package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public interface FilmStorage {

    Collection<Film> getAllFilms(Set<Long> genresIds, Long mpaId);

    Film addFilm(Film film, Set<Long> genresIds, Long mpaId);

    Film updateFilm(Film film, Set<Long> genresIds, Long mpaId);

    Optional<Film> getFilmById(Long id);

    void likeFilm(Long id, Long userId);

    void unlikeFilm(Long id, Long userId);

    List<Film> getPopularFilms(Integer count);
}
