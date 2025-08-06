package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.dtos.FilmDto;
import ru.yandex.practicum.filmorate.dto.requests.films_requests.NewFilmRequest;
import ru.yandex.practicum.filmorate.dto.requests.films_requests.UpdateFilmRequest;
import ru.yandex.practicum.filmorate.exception.exeptions.ElementNotFoundException;
import ru.yandex.practicum.filmorate.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
public class FilmService {

    private final FilmStorage filmStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public Collection<FilmDto> getAllFilms(Set<Long> genresIds, Long mpaId) {
        return filmStorage.getAllFilms(genresIds, mpaId)
                .stream()
                .map(FilmMapper::mapFilmToDto)
                .toList();
    }

    public FilmDto addFilm(NewFilmRequest request, Set<Long> genresIds, Long mpaId) {
        Film film = FilmMapper.mapToFilm(request);
        film = filmStorage.addFilm(film, genresIds, mpaId);
        return FilmMapper.mapFilmToDto(film);
    }

    public FilmDto updateFilm(long filmId, UpdateFilmRequest request, Set<Long> genresIds, Long mpaId) {
        Film updatedFilm = filmStorage.getFilmById(filmId)
                .map(film -> FilmMapper.updateFilmFields(film, request))
                .orElseThrow(() -> new ElementNotFoundException("Film not found"));

        updatedFilm = filmStorage.updateFilm(updatedFilm, genresIds, mpaId);
        return FilmMapper.mapFilmToDto(updatedFilm);
    }

    public FilmDto getFilmById(Long id) {
        return filmStorage.getFilmById(id)
                .map(FilmMapper::mapFilmToDto)
                .orElseThrow(() -> new ElementNotFoundException("Film not found"));
    }

    public void likeFilm(Long id, Long userId) {
        filmStorage.likeFilm(id, userId);
    }

    public void unLikeFilm(Long id, Long userId) {
        filmStorage.unlikeFilm(id, userId);
    }

    public List<FilmDto> getPopularFilms(Integer count) {
        return filmStorage.getPopularFilms(count)
                .stream()
                .map(FilmMapper::mapFilmToDto)
                .toList();
    }
}
