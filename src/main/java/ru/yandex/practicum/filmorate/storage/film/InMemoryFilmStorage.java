package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.exeptions.ElementNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.util.FilmsLikesComparator;

import java.util.*;


@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {

    public static final String RELEASE_DATE_BEFORE = "1895-12-28";

    private final FilmsLikesComparator filmsLikesComparator;

    @Autowired
    public InMemoryFilmStorage(FilmsLikesComparator filmsLikesComparator) {
        this.filmsLikesComparator = filmsLikesComparator;
    }

    public final Map<Long, Film> movies = new HashMap<>();


    @Override
    public Collection<Film> getAllFilms() {
        log.info("Getting films...");
        return movies.values();
    }

    @Override
    public Film addFilm(Film film) {
        log.info("Start addition of film...");
        film.setId(getNexId());
        film.setLikes(0L);
        movies.put(film.getId(), film);
        return film;
    }

    @Override
    public Film updateFilm(Film newFilm) {
        log.trace("Вызван метод updateFilm");
        Film film = getFilmById(newFilm.getId());
        movies.put(film.getId(), newFilm);
        return movies.get(film.getId());
    }

    @Override
    public Film getFilmById(Long id) {

        log.trace("Получаем фильм по id...");
        Film film = movies.get(id);

        if (film == null) {
            throw new ElementNotFoundException("Film not found");
        }

        return film;
    }

    @Override
    public Film likeFilm(Long id, Long userId) {

        Film film = movies.get(id);

        if (film == null) {
            throw new ElementNotFoundException("Film not found");
        }

        Long filmLikes = film.getLikes();
        Collection<Long> usersIdsLiked = film.getUsersIdsLiked();

        if (!usersIdsLiked.contains(userId)) {
            film.setLikes(filmLikes + 1);
        }

        return film;
    }

    @Override
    public Film unlikeFilm(Long id, Long userId) {

        Film film = movies.get(id);

        if (film == null) {
            throw new ElementNotFoundException("Film not found");

        }

        Long filmLikes = film.getLikes();
        Collection<Long> usersIdsLiked = film.getUsersIdsLiked();

        if (usersIdsLiked.contains(userId)) {
            film.setLikes(filmLikes - 1);
        }

        return film;
    }

    @Override
    public List<Film> getPopularFilms(Integer count) {

        Optional<Integer> countOptional = Optional.ofNullable(count);

        if (countOptional.isPresent()) {
            return movies.values().stream()
                    .sorted(filmsLikesComparator.reversed())
                    .limit(count)
                    .toList();
        } else {
            return movies.values().stream()
                    .sorted(filmsLikesComparator.reversed())
                    .limit(10)
                    .toList();
        }
    }

    @Override
    public Long getNexId() {
        long currentMaxId = movies.values().stream()
                .mapToLong(Film::getId)
                .max()
                .orElse(0);
        log.debug("currentFilmMaxId {}", currentMaxId);
        return ++currentMaxId;
    }
}
