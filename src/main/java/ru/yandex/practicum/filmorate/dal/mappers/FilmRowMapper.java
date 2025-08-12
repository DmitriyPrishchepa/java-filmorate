package ru.yandex.practicum.filmorate.dal.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Date;

@Service
public class FilmRowMapper implements RowMapper<Film> {
    @Override
    public Film mapRow(ResultSet rs, int rowNum) throws SQLException {

        Date filmReleaseDate = rs.getDate("release_date");

        return Film.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .releaseDate(filmReleaseDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .duration(rs.getInt("duration"))
                .mpaId(rs.getInt("mpa_id"))
                .build();
    }
}
