package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dal.BaseRepository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.util.LocalDateToTimeStamp;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class FilmDbStorage extends BaseRepository<Film> implements FilmStorage {

    public FilmDbStorage(
            JdbcTemplate jdbc,
            RowMapper<Film> mapper
    ) {
        super(jdbc, mapper);
    }

    @Override
    public List<Film> getAllFilms() {
        final String FIND_ALL_QUERY =
                "SELECT * FROM films";
        ;
        return findMany(FIND_ALL_QUERY);
    }

    @Override
    public Film addFilm(Film film) {

        final String INSERT_QUERY = "INSERT INTO films(name, description, release_date, duration, mpa_id) " +
                "VALUES (?, ?, ?, ?, ?)";

        log.debug("film {}", film);

        Timestamp timestamp = LocalDateToTimeStamp.localDateToTimeStamp(film);

        try {
            Integer id = insert(
                    INSERT_QUERY,
                    film.getName(),
                    film.getDescription(),
                    timestamp,
                    film.getDuration(),
                    film.getMpa().getId()
            );

            film.setId(id);
            batchUpdate(film);
        } catch (
                RuntimeException e) {
            e.getStackTrace();
            System.out.println(Arrays.toString(e.getStackTrace()));
        }

        return film;
    }

    @Override
    public Film updateFilm(Film film) {

        final String UPDATE_QUERY = "UPDATE films SET " +
                "name = ?, description = ?, release_date = ?, duration = ?, mpa_id = ? WHERE id = ?";

        try {
            update(
                    UPDATE_QUERY,
                    film.getName(),
                    film.getDescription(),
                    film.getReleaseDate(),
                    film.getDuration(),
                    film.getMpa().getId()
            );

            batchUpdate(film);

            return film;

        } catch (
                RuntimeException e) {
            e.getStackTrace();
            System.out.println(Arrays.toString(e.getStackTrace()));
        }

        return film;
    }

    @Override
    public Optional<Film> getFilmById(Integer id) {
        final String FIND_BY_ID_QUERY = "SELECT * FROM films WHERE id = ?";
        try {
            return findOne(FIND_BY_ID_QUERY, id);
        } catch (RuntimeException e) {
            e.getStackTrace();
            System.out.println(Arrays.toString(e.getStackTrace()));
        }

        return findOne(FIND_BY_ID_QUERY, id);
    }

    @Override
    public void likeFilm(Integer id, Integer userId) {
        final String LIKE_FILM_QUERY = "INSERT INTO likes(film_id, user_id) VALUES (?, ?);";

        try {
            update(LIKE_FILM_QUERY, id, userId);
        } catch (RuntimeException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    @Override
    public void unlikeFilm(Long id, Long userId) {
        final String UNLIKE_FILM_QUERY = "DELETE FROM likes WHERE film_id = ? AND user_id = ?";
        update(UNLIKE_FILM_QUERY, id, userId);
    }

    @Override
    public List<Film> getPopularFilms(Integer count) {
        final String GET_POPULAR_QUERY =
                "SELECT f.id, f.name, f.description, f.release_date, f.duration, f.mpa_id " +
                        "FROM films f " +
                        "JOIN likes l ON f.id = l.film_id " +
                        "GROUP BY f.id, f.name, f.description, f.release_date, f.duration, f.mpa_id " +
                        "ORDER BY COUNT(l.user_id) DESC " +
                        "LIMIT ?";
        try {
            return findMany(GET_POPULAR_QUERY, count);
        } catch (RuntimeException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
        return findMany(GET_POPULAR_QUERY, count);
    }

    public void batchUpdate(final Film film) {
        final String INSERT_INTO_FILM_GENRES = "INSERT INTO film_genres (film_id, genre_id) VALUES (?, ?)";
        jdbc.batchUpdate(
                INSERT_INTO_FILM_GENRES,
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setInt(1, film.getId());
                        ps.setInt(2, film.getGenres().get(i).getId());
                    }

                    @Override
                    public int getBatchSize() {
                        return film.getGenres().size();
                    }
                });
    }
}
