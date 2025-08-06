package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dal.BaseRepository;
import ru.yandex.practicum.filmorate.model.Film;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

@Component
public class FilmDbStorage extends BaseRepository<Film> implements FilmStorage {

    private static final String FIND_ALL_QUERY =
            "SELECT * " +
                    "FROM films AS f " +
                    "JOIN film_genres AS fg ON f.id = fg.film_id " +
                    "JOIN mpa AS m ON f.mpa_id = m.id " +
                    "WHERE fg.genre_id = ? AND m.id = ?";
    ;
    private static final String INSERT_QUERY = "INSERT INTO films(name, description, release_date, duration, mpa_id) " +
            "VALUES (?, ?, ?, ?) returning id";
    private static final String UPDATE_QUERY = "UPDATE films SET " +
            "name = ?, description = ?, release_date = ?, duration = ?, mpa_id = ? WHERE id = ?";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM films WHERE id = ?";
    private static final String LIKE_FILM_QUERY = "UPDATE likes SET like = 1 WHERE film_id = ? AND user_id = ?";
    private static final String UNLIKE_FILM_QUERY = "DELETE FROM likes WHERE film_id = ? AND user_id = ?";
    private static final String GET_POPULAR_QUERY =
            "SELECT film_id, COUNT(like_id) AS likes_count " +
                    "FROM likes " +
                    "GROUP BY film_id " +
                    "ORDER BY likes_count DESC " +
                    "LIMIT ?";
    private static final String INSERT_INTO_FILM_GENRES = "INSERT INTO film_genres(film_id, genre_id) VALUES(?, ?)";
    private static final String UPDATE_GENRES_IDS = "UPDATE film_genres SET genre_id = ? WHERE film_id = ?";

    public FilmDbStorage(JdbcTemplate jdbc, @Qualifier("filmRowMapper") RowMapper<Film> mapper) {
        super(jdbc, mapper);
    }

    @Override
    public Collection<Film> getAllFilms(Set<Long> genresIds, Long mpaId) {
        List<Film> films = new ArrayList<>();
        for (Long genreId : genresIds) {
            films.addAll(findMany(FIND_ALL_QUERY, genreId, mpaId));
        }

        return films;
    }

    @Override
    public Film addFilm(Film film, Set<Long> genresIds, Long mpaId) {
        long id = insert(
                INSERT_QUERY,
                film.getName(),
                film.getDescription(),
                Timestamp.from(Instant.from(film.getReleaseDate())),
                film.getDuration(),
                mpaId
        );
        film.setId(id);

        for (Long genreId : genresIds) {
            insert(
                    INSERT_INTO_FILM_GENRES,
                    id,
                    genreId
            );
        }

        return film;
    }

    @Override
    public Film updateFilm(Film film, Set<Long> genresIds, Long mpaId) {
        update(
                UPDATE_QUERY,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                mpaId
        );

        for (Long genreId : genresIds) {
            update(
                    UPDATE_GENRES_IDS,
                    film.getId(),
                    genreId
            );
        }

        return film;
    }

    @Override
    public Optional<Film> getFilmById(Long id) {
        return findOne(FIND_BY_ID_QUERY, id);
    }

    @Override
    public void likeFilm(Long id, Long userId) {
        update(LIKE_FILM_QUERY, id, userId);
    }

    @Override
    public void unlikeFilm(Long id, Long userId) {
        update(UNLIKE_FILM_QUERY, id, userId);
    }

    @Override
    public List<Film> getPopularFilms(Integer count) {
        return findMany(GET_POPULAR_QUERY, count);
    }
}
