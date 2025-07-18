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
    public Film updateFilm(Film newFilm) {
        log.trace("Вызван метод updateFilm");

        Film film = getFilmById(newFilm.getId());

        if (film == null) {
            throw new ElementNotFoundException("Film not found");
        }

        newFilm.setLikes(film.getLikes());
        newFilm.setUsersIdsLiked(film.getUsersIdsLiked());
        movies.put(newFilm.getId(), newFilm);
        return newFilm;
    }

    @Override
    public Film getFilmById(Long id) {
        log.trace("Получаем фильм по id...");
        return movies.get(id);
    }

    @Override
    public void likeFilm(Long id, Long userId) {
        Film film = movies.get(id);

        if (film == null) {
            throw new ElementNotFoundException("Film not found");
        }

        Long filmLikes = film.getLikes();
        Collection<Long> usersIdsLiked = film.getUsersIdsLiked();

        if (us.getUserById(userId) == null) {
            throw new ElementNotFoundException("Unknown user");
        }

        if (!usersIdsLiked.contains(userId)) {
            usersIdsLiked.add(userId);
            film.setLikes(filmLikes + 1);
        }
    }

    @Override
    public void unlikeFilm(Long id, Long userId) {
        Film film = getFilmById(id);

        if (film == null) {
            throw new ElementNotFoundException("Film not found");
        }

        Long filmLikes = film.getLikes();
        Set<Long> usersIdsLiked = film.getUsersIdsLiked();

        if (us.getUserById(userId) == null) {
            throw new ElementNotFoundException("Unknown user");
        }

        if (usersIdsLiked.contains(userId)) {
            film.setLikes(filmLikes - 1);
            usersIdsLiked.remove(userId);
            film.setUsersIdsLiked(usersIdsLiked);
        }
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

    private Integer getPopularCount(Integer count) {
        if (count > movies.size()) {
            return movies.size();
        }

        return count;
    }
}
