package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dal.BaseRepository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genres.GenreStorage;
import ru.yandex.practicum.filmorate.storage.genres.GenresDbStorage;
import ru.yandex.practicum.filmorate.util.LocalDateToTimeStamp;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
public class FilmDbStorage extends BaseRepository<Film> implements FilmStorage {

    private GenreStorage genreStorage;

    private static final String FIND_ALL_QUERY =
            "SELECT * FROM films";
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
    private static final String INSERT_INTO_FILM_GENRES = "INSERT INTO film_genres (film_id, genre_id) VALUES (?, ?)";
    private static final String UPDATE_GENRES_IDS = "UPDATE film_genres SET genre_id = ? WHERE film_id = ?";

    public FilmDbStorage(
            JdbcTemplate jdbc,
            RowMapper<Film> mapper,
            GenresDbStorage genresDbStorage
    ) {
        super(jdbc, mapper);
        this.genreStorage = genresDbStorage;
    }

    @Override
    public Collection<Film> getAllFilms() {
        return findMany(FIND_ALL_QUERY);
    }

    @Override
    public Film addFilm(Film film) {

        Timestamp timestamp = LocalDateToTimeStamp.localDateToTimeStamp(film);

        try {
            Integer mpaId = film.getMpaId();
            if (mpaId == null) {
                mpaId = 1;
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
            List<Genre> genres = genreStorage.getGenresByFilmId(id);
            film.setGenres(genres);

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

            batchUpdate(film);

        } catch (
                RuntimeException e) {
            e.getStackTrace();
            System.out.println(Arrays.toString(e.getStackTrace()));
        }

        return film;
    }

    @Override
    public Optional<Film> getFilmById(Integer id) {
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

    public void batchUpdate(final Film film) {
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
