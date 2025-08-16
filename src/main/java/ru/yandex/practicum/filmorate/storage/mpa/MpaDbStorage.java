package ru.yandex.practicum.filmorate.storage.mpa;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dal.BaseRepository;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;
import java.util.Optional;

@Component
public class MpaDbStorage extends BaseRepository<Mpa> implements MpaStorage {

    private static final String GET_RATINGS_QUERY = "SELECT * FROM mpa";
    private static final String GET_RATING_BY_ID = "SELECT * FROM mpa WHERE id = ?";

    public MpaDbStorage(JdbcTemplate jdbc, RowMapper<Mpa> mapper) {
        super(jdbc, mapper);
    }

    @Override
    public List<Mpa> getRatings() {
        return findMany(GET_RATINGS_QUERY);
    }

    @Override
    public Optional<Mpa> getRatingById(Integer id) {
        return findOne(GET_RATING_BY_ID, id);
    }
}
