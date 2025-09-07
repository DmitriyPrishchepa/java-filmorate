package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.exeptions.ElementNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.util.FilmUpdater;
import ru.yandex.practicum.filmorate.validation.FilmValidator;

import java.util.*;

@Service
@Slf4j
public class FilmService {

    private final FilmStorage filmStorage;
    private final MpaService mpaService;
    private final GenreService genreService;

    @Autowired
    public FilmService(FilmStorage filmStorage, MpaService mpaService, GenreService genreService) {
        this.filmStorage = filmStorage;
        this.mpaService = mpaService;
        this.genreService = genreService;
    }

    public Collection<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }

    public Film addFilm(Film request) {

        FilmValidator.validateFilm(request);

        List<Genre> genres = new ArrayList<>(request.getGenres());

        request.getGenres().clear();

        for (Genre genre : genres) {
            if (genre.getId() > 6) {
                throw new ElementNotFoundException("Genre not found");
            }
            Genre genre1 = genreService.getGenreById(genre.getId());
            request.getGenres().add(genre1);
        }

        Integer mpaIdFromRequest = request.getMpa().getId();

        if (mpaIdFromRequest != null) {
            try {
                if (mpaIdFromRequest > 5) {
                    throw new ElementNotFoundException("Mpa not found");
                }
                Mpa mpa = mpaService.getMpaById(mpaIdFromRequest);
                request.setMpa(
                        Mpa.builder()
                                .id(mpaIdFromRequest)
                                .name(mpa.getName())
                                .build());
            } catch (RuntimeException e) {
                throw new ElementNotFoundException("Not mpa for this film");
            }
        }

        return filmStorage.addFilm(request);
    }

    public Film updateFilm(Film request) {
        log.debug("id {}", request.getId());
        Film existingFilm = getFilmById(request.getId());
        FilmValidator.validateFilm(existingFilm);
        Film updatedFilm = FilmUpdater.updateFieldsOfFilm(existingFilm, request);
        return filmStorage.updateFilm(updatedFilm);
    }

    public Film getFilmById(Integer id) {

        List<Genre> loadGenres = genreService.getGenresOfFilm(id);

        return filmStorage.getFilmById(id)
                .map(film -> {
                    Set<Genre> uniqueGenres = new HashSet<>(loadGenres);
                    film.getGenres().addAll(uniqueGenres);

                    log.debug("uniqueGenres.size {}", uniqueGenres.size());
                    log.debug("uniqueGenres.size {}", film.getGenres().size());

                    Mpa mpa = mpaService.getMpaById(film.getMpa().getId());

                    film.setMpa(Mpa.builder()
                            .id(film.getMpa().getId())
                            .name(mpa.getName())
                            .build());

                    return film;
                })
                .orElseThrow(() -> new ElementNotFoundException("Film not found"));
    }

    public void likeFilm(Integer id, Integer userId) {
        filmStorage.likeFilm(id, userId);
    }

    public void unLikeFilm(Integer id, Integer userId) {
        filmStorage.unlikeFilm(id, userId);
    }

    public List<Film> getPopularFilms(Integer count) {
        return filmStorage.getPopularFilms(count);
    }

    public Integer getLikesOfFilm(Integer id) {
        return filmStorage.getLikesOfFilm(id);
    }
}
