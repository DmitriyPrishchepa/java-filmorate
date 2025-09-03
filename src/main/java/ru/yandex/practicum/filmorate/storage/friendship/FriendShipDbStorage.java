package ru.yandex.practicum.filmorate.storage.friendship;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dal.BaseRepository;
import ru.yandex.practicum.filmorate.model.FriendShip;

import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class FriendShipDbStorage extends BaseRepository<FriendShip> implements FriendShipStorage {
    public FriendShipDbStorage(JdbcTemplate jdbc, RowMapper<FriendShip> mapper) {
        super(jdbc, mapper);
    }

    @Override
    public void addFriend(Integer userId, Integer friendId) {
        String sqlQuery = "INSERT INTO friendship (user_id, friend_id) VALUES (?, ?)";

        int rows = jdbc.update(sqlQuery, userId, friendId);
        System.out.println("Rows inserted: " + rows);

        List<Map<String, Object>> debugRows = jdbc.queryForList("SELECT * FROM friendship");
        System.out.println("DEBUG friendship table: " + debugRows);

        for (Object row : debugRows) {
            log.debug("row {}", row);
        }
    }

    @Override
    public void removeUserFromFriends(Integer id, Integer friendId) {
        final String REMOVE_FROM_FRIEND = "DELETE FROM friendship WHERE user_id = ? AND friend_id = ?";
        update(REMOVE_FROM_FRIEND, id, friendId);
    }

    @Override
    public List<FriendShip> getAllFriendship() {
        final String GET_ALL = "SELECT * FROM friendship";
        return findMany(GET_ALL);
    }
}
