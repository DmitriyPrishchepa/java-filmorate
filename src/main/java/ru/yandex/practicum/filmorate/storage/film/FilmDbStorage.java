package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import ru.yandex.practicum.filmorate.dal.BaseRepository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.util.LocalDateToTimeStamp;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
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
            "VALUES (?, ?, ?, ?, ?)";
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
    private static final String INSERT_INTO_FILM_GENRES = "INSERT INTO film_genres (film_id, genre_id) VALUES (%d, %d)";
    private static final String UPDATE_GENRES_IDS = "UPDATE film_genres SET genre_id = ? WHERE film_id = ?";

    public FilmDbStorage(JdbcTemplate jdbc, RowMapper<Film> mapper) {
        super(jdbc, mapper);
    }

    @Override
    public Collection<Film> getAllFilms(Set<Integer> genresIds, Integer mpaId) {
        List<Film> films = new ArrayList<>();
        for (Integer genreId : genresIds) {
            films.addAll(findMany(FIND_ALL_QUERY, genreId, mpaId));
        }

        return films;
    }

    @Override
    public Film addFilm(Film film) {

        Timestamp timestamp = LocalDateToTimeStamp.localDateToTimeStamp(film);

        try {
            Integer mpaId = film.getMpaId();
            if (mpaId == null) {
                mpaId = 1; // Замените DEFAULT_MPA_ID на ваше дефолтное значение
            }

            Integer id = insert(
                    INSERT_QUERY,
                    film.getName(),
                    film.getDescription(),
                    timestamp,
                    film.getDuration(),
                    mpaId
            );

            film.setId(id);

            if (film.getGenres() != null) {
                batchUpdate(film);
            }

            return film;
        } catch (
                RuntimeException e) {
            e.getStackTrace();
            System.out.println(Arrays.toString(e.getStackTrace()));
        }

        return film;
    }

    @Override
    public Film updateFilm(Film film) {

        try {
            update(
                    UPDATE_QUERY,
                    film.getName(),
                    film.getDescription(),
                    film.getReleaseDate(),
                    film.getDuration(),
                    film.getMpaId()
            );

            if (film.getGenres() != null) {
                batchUpdate(film);
            }

            return film;
        } catch (
                RuntimeException e) {
            e.getStackTrace();
            System.out.println(Arrays.toString(e.getStackTrace()));
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

    public void batchUpdate(Film film) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        jdbc.batchUpdate(INSERT_INTO_FILM_GENRES, new BatchPreparedStatementSetter() {

            final ArrayList<Integer> genres = new ArrayList<>(film.getGenres());

            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {

                preparedStatement.setLong(1, film.getId());
                preparedStatement.setLong(2, genres.get(i));
            }

            @Override
            public int getBatchSize() {
                return genres.size();
            }
        });
        stopWatch.stop();
    }
}
