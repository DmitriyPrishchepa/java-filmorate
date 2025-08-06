package ru.yandex.practicum.filmorate.storage.genres;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Optional;

@Component
public interface GenreStorage {
    List<Genre> getGenres();

    Optional<Genre> getGenreById(long id);
}
