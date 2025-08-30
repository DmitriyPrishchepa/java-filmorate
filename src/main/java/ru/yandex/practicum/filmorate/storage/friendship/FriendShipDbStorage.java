package ru.yandex.practicum.filmorate.storage.friendship;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dal.BaseRepository;
import ru.yandex.practicum.filmorate.model.FriendShip;

import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class FriendShipDbStorage extends BaseRepository<FriendShip> implements FriendShipStorage {
    public FriendShipDbStorage(JdbcTemplate jdbc, RowMapper<FriendShip> mapper) {
        super(jdbc, mapper);
    }

    @Override
    public void addFriend(Integer userId, Integer friendId) {
        try {
            String sqlQuery = "INSERT INTO friendship(user_id, friend_id) VALUES(?, ?)";
            update(sqlQuery, userId, friendId);
        } catch (RuntimeException e) {
            e.getStackTrace();
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    @Override
    public void removeUserFromFriends(Integer id, Integer friendId) {
        final String REMOVE_FROM_FRIEND =
                "DELETE FROM friendship WHERE user_id = ? AND friend_id = ?";
        update(REMOVE_FROM_FRIEND, id, friendId);
    }

    @Override
    public List<FriendShip> getCommonFriends(Integer userId, Integer otherUserId) {

        final String GET_COMMON_FRIENDS =
                "SELECT f1.friend_id " +
                        "FROM friendship f1 " +
                        "JOIN friendship f2 " +
                        "ON f1.friend_id = f2.friend_id " +
                        "WHERE f1.user_id = ? " +
                        "AND f2.user_id = ?;";
        return findMany(GET_COMMON_FRIENDS, userId, otherUserId);
    }

    @Override
    public List<FriendShip> friendGet(Integer userId) {
        final String FRIEND_GET =
                "SELECT * FROM users u " +
                        "JOIN friendship f " +
                        "ON u.id = f.friend_id " +
                        "WHERE u.id = ?";
        return findMany(FRIEND_GET, userId);
    }
}
