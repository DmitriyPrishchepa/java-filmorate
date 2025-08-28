package ru.yandex.practicum.filmorate.dal.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.FriendShip;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class FriendShipRowMapper implements RowMapper<FriendShip> {
    @Override
    public FriendShip mapRow(ResultSet rs, int rowNum) throws SQLException {
        return FriendShip.builder()
                .user(User
                        .builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .login(rs.getString("login"))
                        .email(rs.getString("email"))
                        .birthday(rs.getDate("birthday").toLocalDate())
                        .build())
                .friend(User
                        .builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .login(rs.getString("login"))
                        .email(rs.getString("email"))
                        .birthday(rs.getDate("birthday").toLocalDate())
                        .build())
                .status(rs.getString("status"))
                .build();
    }
}
