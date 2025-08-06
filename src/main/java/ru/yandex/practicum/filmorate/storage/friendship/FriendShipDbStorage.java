package ru.yandex.practicum.filmorate.storage.friendship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dal.BaseRepository;
import ru.yandex.practicum.filmorate.model.FriendShip;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;

@Component
public class FriendShipDbStorage extends BaseRepository<FriendShip> implements FriendShipStorage {

    private static final String ADD_TO_FRIENDS_REQUEST_QUERY =
            "INSERT INTO friendship(user_id, friend_id, status) VALUES(?, ?, 'ожидает подтверждения')";

    private static final String UPDATE_APPROVED_FRIENDSHIP_STATUS_QUERY =
            "UPDATE friendship SET status = 'принято' WHERE user_id = ? AND friend_id = ?";

    private static final String UPDATE_REFUSED_FRIENDSHIP_STATUS_QUERY =
            "UPDATE friendship SET status = 'отклонено' WHERE user_id = ? AND friend_id = ?";

    private UserStorage userStorage;

    @Autowired
    public FriendShipDbStorage(JdbcTemplate jdbc, RowMapper<FriendShip> mapper, UserStorage userStorage) {
        super(jdbc, mapper);
        this.userStorage = userStorage;
    }

    @Override
    public List<User> sendFriendShipRequest(Long user_id, Long friend_id) {
        insert(ADD_TO_FRIENDS_REQUEST_QUERY, user_id, friend_id);
        return userStorage.getAllFriends(user_id);
    }

    @Override
    public List<User> approveFriendShipRequest(Long user_id, Long friend_id) {
        update(UPDATE_APPROVED_FRIENDSHIP_STATUS_QUERY, user_id, friend_id);
        return userStorage.getAllFriends(user_id);
    }

    @Override
    public List<User> refuseFriendShipRequest(Long user_id, Long friend_id) {
        update(UPDATE_REFUSED_FRIENDSHIP_STATUS_QUERY, user_id, friend_id);
        return userStorage.getAllFriends(user_id);
    }
}
