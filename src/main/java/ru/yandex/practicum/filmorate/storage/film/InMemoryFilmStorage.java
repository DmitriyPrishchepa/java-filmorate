package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.exeptions.ElementNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.util.FilmsLikesComparator;

import java.util.*;


@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {

    public static final String RELEASE_DATE_BEFORE = "1895-12-28";

    private final FilmsLikesComparator filmsLikesComparator;
    private final UserStorage us;

    @Autowired
    public InMemoryFilmStorage(
            FilmsLikesComparator filmsLikesComparator,
            UserStorage us
    ) {
        this.filmsLikesComparator = filmsLikesComparator;
        this.us = us;
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
        film.setUsersIdsLiked(new HashSet<>());
        movies.put(film.getId(), film);
        return film;
    }

    @Override
    public Optional<Film> updateFilm(Film newFilm) {
        log.trace("Вызван метод updateFilm");
        return Optional.ofNullable(movies.computeIfPresent(newFilm.getId(), (k, v) -> newFilm));
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
    public Long likeFilm(Long id, Long userId) {
        Film film = movies.get(id);
        Long filmLikes = film.getLikes();
        Collection<Long> usersIdsLiked = film.getUsersIdsLiked();

        if (us.getUserById(userId) == null) {
            throw new ElementNotFoundException("Unknown user");
        }

        if (!usersIdsLiked.contains(userId)) {
            usersIdsLiked.add(userId);
            film.setLikes(filmLikes + 1);
            return film.getLikes();
        }

        return 0L;
    }

    @Override
    public Long unlikeFilm(Long id, Long userId) {
        Film film = movies.get(id);
        Long filmLikes = film.getLikes();
        Set<Long> usersIdsLiked = film.getUsersIdsLiked();

        if (us.getUserById(userId) == null) {
            throw new ElementNotFoundException("Unknown user");
        }

        if (usersIdsLiked.contains(userId)) {
            film.setLikes(filmLikes - 1);
            usersIdsLiked.remove(userId);
            film.setUsersIdsLiked(usersIdsLiked);

            return film.getLikes();
        }

        return 0L;
    }

    @Override
    public List<Film> getPopularFilms(Integer count) {
        return movies.values().stream()
                .sorted(filmsLikesComparator.reversed())
                .limit(Objects.requireNonNullElse(count, 10))
                .toList();
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
