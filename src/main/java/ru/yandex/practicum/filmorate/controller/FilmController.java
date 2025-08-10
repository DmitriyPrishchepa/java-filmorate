package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.dtos.FilmDto;
import ru.yandex.practicum.filmorate.dto.requests.films_requests.NewFilmRequest;
import ru.yandex.practicum.filmorate.dto.requests.films_requests.UpdateFilmRequest;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/films")
@Slf4j
@Validated
public class FilmController {

    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<FilmDto> findAllFilms(@RequestParam(value = "genresIds", required = false) Set<Long> genresIds,
                                            @RequestParam(value = "mpaId", required = false) Long mpaId) {
        return filmService.getAllFilms(genresIds, mpaId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FilmDto addFilm(@Valid @RequestBody NewFilmRequest film) {
        return filmService.addFilm(film);
    }

    @PutMapping("/{id}")
    public FilmDto updateFilm(@PathVariable("id") long id,
                              @Valid @RequestBody UpdateFilmRequest film) {
        return filmService.updateFilm(id, film);
    }

    @GetMapping("/{id}")
    public FilmDto getFilmById(@Valid @PathVariable("id") @Positive Long id) {
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
    @ResponseStatus(HttpStatus.OK)
    public List<FilmDto> getPopularFilms(@Positive @RequestParam(required = false) Integer count) {
        return filmService.getPopularFilms(count);
    }
}