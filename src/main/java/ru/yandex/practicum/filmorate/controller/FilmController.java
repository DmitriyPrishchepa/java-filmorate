package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/films")
@Slf4j
@Validated
public class FilmController {

    private final FilmService filmService;
    public static final String DEFAULT_FILM_LIST_SIZE = "10";

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<Film> findAllFilms() {
        return filmService.getAllFilms();
    }

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        return filmService.addFilm(film);
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        return filmService.updateFilm(film);
    }

    @GetMapping("/{id}")
    public Film getFilmById(@Valid @PathVariable("id") @Positive Long id) {
        return filmService.getFilmById(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public void likeFilm(@PathVariable @Positive Long id, @Positive @PathVariable Long userId) {
        filmService.likeFilm(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void unlikeFilm(@PathVariable @Positive Long id, @Positive @PathVariable Long userId) {
        filmService.unLikeFilm(id, userId);
    }

    @GetMapping("/popular")
    public List<Film> getPopularFilms(@Positive @RequestParam(defaultValue = DEFAULT_FILM_LIST_SIZE) Integer count) {
        return filmService.getPopularFilms(count);
    }
}