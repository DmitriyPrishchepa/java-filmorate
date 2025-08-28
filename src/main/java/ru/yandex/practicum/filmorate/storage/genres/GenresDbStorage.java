package ru.yandex.practicum.filmorate.storage.genres;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dal.BaseRepository;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Optional;

@Component
public class GenresDbStorage extends BaseRepository<Genre> implements GenreStorage {

    public GenresDbStorage(JdbcTemplate jdbc, RowMapper<Genre> mapper) {
        super(jdbc, mapper);
    }

    @Override
    public List<Genre> getGenres() {
        final String GET_GENRES_QUERY = "SELECT * FROM genres";
        return findMany(GET_GENRES_QUERY);
    }

    @Override
    public Optional<Genre> getGenreById(Integer id) {
        final String GET_GENRES_BY_ID_QUERY = "SELECT * FROM genres WHERE id = ?";
        return findOne(GET_GENRES_BY_ID_QUERY, id);
    }

    @Override
    public List<Genre> getGenresOfFilm(Integer id) {
        final String GET_GENRES_OF_FILM_QUERY =
                "SELECT g.id AS genre_id, g.name AS genre_name " +
                        "FROM genres AS g " +
                        "JOIN film_genres AS fg ON g.id = fg.genre_id " +
                        "WHERE fg.film_id = ? " +
                        "ORDER BY g.id";

        return findMany(GET_GENRES_OF_FILM_QUERY, id);
    }
}
