package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.exeptions.ElementNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.util.FilmUpdater;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class FilmService {

    private final FilmStorage filmStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public Collection<Film> getAllFilms(Set<Integer> genresIds, Integer mpaId) {
        return filmStorage.getAllFilms(genresIds, mpaId);
    }

    public Film addFilm(Film request) {
        return filmStorage.addFilm(request);
    }

    public Film updateFilm(long filmId, Film request) {
        Optional<Film> updatedFilmOptional = filmStorage.getFilmById(filmId);

        if (updatedFilmOptional.isPresent()) {
            Film film = updatedFilmOptional.get();
            Film updatedFilm = FilmUpdater.updateFieldsOfFilm(film, request);
            filmStorage.updateFilm(updatedFilm);
            return updatedFilm;
        } else {
            throw new ElementNotFoundException("Film not found");
        }
    }

    public Film getFilmById(Long id) {
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
