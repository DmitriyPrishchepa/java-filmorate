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

    private static final String GET_GENRES_QUERY = "SELECT * FROM genres";
    private static final String GET_GENRES_BY_ID_QUERY = "SELECT * FROM genres WHERE id = ?";
    private static final String GET_GENRES_OF_FILM_QUERY =
            "SELECT g.id" +
                    "FROM genres AS g " +
                    "JOIN film_genres as fg ON g.id = fg.genre_id " +
                    "WHERE fg.film_id = ?";

    public GenresDbStorage(JdbcTemplate jdbc, RowMapper<Genre> mapper) {
        super(jdbc, mapper);
    }

    @Override
    public List<Genre> getGenres() {
        return findMany(GET_GENRES_QUERY);
    }

    @Override
    public Optional<Genre> getGenreById(long id) {
        return findOne(GET_GENRES_BY_ID_QUERY, id);
    }

    @Override
    public List<Genre> getGenresByFilmId(Integer filmId) {
        return findMany(GET_GENRES_OF_FILM_QUERY, filmId);
    }
}
