package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.exeptions.ElementNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genres.GenreStorage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class GenreService {

    private final GenreStorage genreStorage;

    @Autowired
    public GenreService(GenreStorage genreStorage) {
        this.genreStorage = genreStorage;
    }

    public List<Genre> getGenres() {

        List<Genre> uniqueGenresByName = genreStorage.getGenres().stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.toMap(
                                Genre::getName,
                                genre -> genre,
                                (existing, ignored) -> existing),
                        map -> new ArrayList<>(map.values())))
                .stream()
                .sorted(Comparator.comparingInt(Genre::getId))
                .limit(6)
                .collect(Collectors.toList());

        log.debug("size {}", uniqueGenresByName.size());
        return uniqueGenresByName;
    }

    public Genre getGenreById(Integer id) {
        return genreStorage.getGenreById(id)
                .orElseThrow(() -> new ElementNotFoundException("Genre not found"));
    }

    public List<Genre> getGenresOfFilm(Integer id) {
        return genreStorage.getGenresOfFilm(id);
    }
}
