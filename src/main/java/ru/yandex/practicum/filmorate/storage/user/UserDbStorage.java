package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dal.BaseRepository;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
public class UserDbStorage extends BaseRepository<User> implements UserStorage {
    private static final String FIND_ALL_QUERY = "SELECT * FROM users";
    private static final String INSERT_QUERY =
            "INSERT INTO users(name, email, login, birthday) " +
                    "VALUES (?, ?, ?, ?) returning id";
    private static final String UPDATE_QUERY =
            "UPDATE users SET " +
                    "name = ?, email = ?, login = ?, duration = ?, birthday = ? WHERE id = ?";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM users WHERE id = ?";

    private static final String GET_FRIENDS_OF_USER =
            "SELECT * " +
                    "FROM friendship AS f " +
                    "JOIN users AS u ON f.friend_id = u.id " +
                    "WHERE f.user_id = ? AND f.status = true";

    private static final String REMOVE_FROM_FRIEND =
            "DELETE FROM friendship WHERE user_id = ? AND friend_id = ?";
    private static final String GET_COMMON_FRIENDS =
            "SELECT f1.friend_id " +
                    "FROM friendship AS f1 " +
                    "JOIN friendship AS f2 ON f1.friend_id = f2.friend_id " +
                    "WHERE f1.user_id = ? AND f2.user_id = ? AND f1.status = ? AND f2.status = true";

    public UserDbStorage(JdbcTemplate jdbc, RowMapper<User> mapper) {
        super(jdbc, mapper);
    }

    @Override
    public Collection<User> getAllUsers() {
        return findMany(FIND_ALL_QUERY);
    }

    @Override
    public User addUser(User user) {
        long id = insert(
                INSERT_QUERY,
                user.getName(),
                user.getEmail(),
                user.getLogin(),
                Timestamp.from(Instant.from(user.getBirthday()))
        );

        user.setId(id);
        return user;
    }

    @Override
    public User updateUser(User user) {
        update(
                UPDATE_QUERY,
                user.getName(),
                user.getEmail(),
                user.getLogin(),
                user.getBirthday()
        );
        return user;
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return findOne(FIND_BY_ID_QUERY, id);
    }

//    @Override
//    public Collection<User> addUserToFriends(Long userId, Long friendId) {
//        insert(ADD_TO_FRIENDS_REQUEST_QUERY, userId, friendId);
//
//        return getAllFriends(userId);
//    }

    @Override
    public void removeUserFromFriends(Long id, Long friendId) {
        update(REMOVE_FROM_FRIEND, id, friendId);
    }

    @Override
    public List<User> getAllFriends(Long id) {
        return findMany(GET_FRIENDS_OF_USER, id);
    }

    @Override
    public List<User> getCommonFriends(Long userId, Long otherUserId) {
        return findMany(GET_COMMON_FRIENDS, userId, otherUserId);
    }
}
