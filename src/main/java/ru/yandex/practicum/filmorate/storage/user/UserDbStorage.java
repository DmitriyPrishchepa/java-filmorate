package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dal.BaseRepository;
import ru.yandex.practicum.filmorate.exception.exeptions.ElementNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.util.LocalDateToTimeStamp;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class UserDbStorage extends BaseRepository<User> implements UserStorage {
    private static final String FIND_ALL_QUERY = "SELECT * FROM users";
    private static final String INSERT_QUERY =
            "INSERT INTO users(name, email, login, birthday) " +
                    "VALUES (?, ?, ?, ?)";
    private static final String UPDATE_QUERY =
            "UPDATE users SET " +
                    "name = ?, email = ?, login = ?, birthday = ? WHERE id = ?";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM users WHERE id = ?";

    private static final String GET_FRIENDS_OF_USER =
            "SELECT * " +
                    "FROM friendship AS f " +
                    "JOIN users AS u ON f.friend_id = u.id " +
                    "WHERE f.user_id = ? AND f.status = true";

    private static final String ADD_TO_FRIENDS_QUERY =
            "INSERT INTO friendship(user_id, friend_id) VALUES (?, ?)";

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

        Timestamp timestamp = LocalDateToTimeStamp.localDateToTimeStamp(user);

        try {

            Integer id = insert(
                    INSERT_QUERY,
                    user.getName(),
                    user.getEmail(),
                    user.getLogin(),
                    timestamp
            );

            user.setId(id);

            if (user.getName().isBlank()) {
                user.setName(user.getLogin());
            }

            return user;
        } catch (RuntimeException e) {
            e.getStackTrace();
            System.out.println(Arrays.toString(e.getStackTrace()));
        }

        return user;
    }

    @Override
    public User updateUser(User user) {
        Timestamp timestamp = LocalDateToTimeStamp.localDateToTimeStamp(user);

        try {
            update(
                    UPDATE_QUERY,
                    user.getName(),
                    user.getEmail(),
                    user.getLogin(),
                    timestamp
            );

            if (user.getName().isBlank()) {
                user.setName(user.getLogin());
            }

        } catch (RuntimeException e) {
            e.getStackTrace();
            System.out.println(Arrays.toString(e.getStackTrace()));
        }

        return user;
    }

    @Override
    public Optional<User> getUserById(Integer id) {
        return findOne(FIND_BY_ID_QUERY, id);
    }

    @Override
    public void addFriend(Integer userId, Integer friendId) {
        insert(ADD_TO_FRIENDS_QUERY, userId, friendId);
    }

    @Override
    public void removeUserFromFriends(Long id, Long friendId) {
        update(REMOVE_FROM_FRIEND, id, friendId);
    }

    @Override
    public List<User> getAllFriends(Integer id) {
        return findMany(GET_FRIENDS_OF_USER, id);
    }

    @Override
    public List<User> getCommonFriends(Long userId, Long otherUserId) {
        return findMany(GET_COMMON_FRIENDS, userId, otherUserId);
    }

    @Override
    public boolean existsById(int userId) {
        String sql = "SELECT COUNT(*) > 0 FROM users WHERE id = ?";
        Integer count = jdbc.queryForObject(sql, Integer.class, userId);
        return count > 0;
    }
}
