package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.exeptions.ElementNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.util.FilmUpdater;
import ru.yandex.practicum.filmorate.validation.FilmValidator;

import java.util.Collection;
import java.util.List;

@Service
@Slf4j
public class FilmService {

    private final FilmStorage filmStorage;
    private final MpaService mpaService;

    @Autowired
    public FilmService(FilmStorage filmStorage, MpaService mpaService) {
        this.filmStorage = filmStorage;
        this.mpaService = mpaService;
    }

    public Collection<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }

    public Film addFilm(Film request) {
        Mpa mpa = mpaService.getMpaById(request.getMpaId());
        request.setMpaId(mpa.getId());
        return filmStorage.addFilm(request);
    }

    public Film updateFilm(Film request) {
        Film existingFilm = getFilmById(request.getId());
        Film validatedFilm = FilmValidator.validateFilm(existingFilm);
        Film updatedFilm = FilmUpdater.updateFieldsOfFilm(existingFilm, validatedFilm);
        return filmStorage.updateFilm(updatedFilm);
    }

    public Film getFilmById(Integer id) {
        return filmStorage.getFilmById(id)
                .orElseThrow(() -> new ElementNotFoundException("Film not found"));
    }

    public void likeFilm(Long id, Long userId) {
        filmStorage.likeFilm(id, userId);
    }

    public void unLikeFilm(Long id, Long userId) {
        filmStorage.unlikeFilm(id, userId);
    }

    public List<Film> getPopularFilms(Integer count) {
        return filmStorage.getPopularFilms(count);
    }
}
