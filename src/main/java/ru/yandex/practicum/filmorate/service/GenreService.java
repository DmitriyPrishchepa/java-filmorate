package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.dtos.GenreDto;
import ru.yandex.practicum.filmorate.exception.exeptions.ElementNotFoundException;
import ru.yandex.practicum.filmorate.mapper.GenreMapper;
import ru.yandex.practicum.filmorate.storage.genres.GenreStorage;

import java.util.List;

@Service
public class GenreService {

    private final GenreStorage genreStorage;

    @Autowired
    public GenreService(GenreStorage genreStorage) {
        this.genreStorage = genreStorage;
    }

    public List<GenreDto> getGenres() {
        return genreStorage.getGenres()
                .stream()
                .map(GenreMapper::mapGenreToDto)
                .toList();
    }

    public GenreDto getGenreById(long id) {
        return genreStorage.getGenreById(id)
                .map(GenreMapper::mapGenreToDto)
                .orElseThrow(() -> new ElementNotFoundException("Genre not found"));
    }
}
