package ru.yandex.practicum.filmorate.dal.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.FriendShip;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class FriendShipRowMapper implements RowMapper<FriendShip> {
    @Override
    public FriendShip mapRow(ResultSet rs, int rowNum) throws SQLException {
        return FriendShip.builder()
                .userId(rs.getInt("user_id"))
                .friendId(rs.getInt("friend_id"))
                .status(rs.getString("status"))
                .build();
    }
}
