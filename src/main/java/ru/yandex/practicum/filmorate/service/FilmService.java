package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.exeptions.ElementNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.util.FilmUpdater;
import ru.yandex.practicum.filmorate.validation.FilmValidator;

import java.util.Collection;
import java.util.List;

@Service
public class FilmService {

    private final FilmStorage filmStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public Collection<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }

    public Film addFilm(Film request) {
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
