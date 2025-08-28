package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dal.BaseRepository;
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

    public UserDbStorage(JdbcTemplate jdbc, RowMapper<User> mapper) {
        super(jdbc, mapper);
    }

    @Override
    public Collection<User> getAllUsers() {
        final String FIND_ALL_QUERY = "SELECT * FROM users";
        return findMany(FIND_ALL_QUERY);
    }

    @Override
    public User addUser(User user) {
        final String INSERT_QUERY =
                "INSERT INTO users(name, email, login, birthday) " +
                        "VALUES (?, ?, ?, ?)";

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
        final String UPDATE_QUERY =
                "UPDATE users SET " +
                        "name = ?, email = ?, login = ?, birthday = ? WHERE id = ?";

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
        final String FIND_BY_ID_QUERY = "SELECT * FROM users WHERE id = ?";
        return findOne(FIND_BY_ID_QUERY, id);
    }

    @Override
    public void addFriend(Integer userId, Integer friendId) {
        final String ADD_TO_FRIENDS_QUERY =
                "INSERT INTO friendship(user_id, friend_id, status) VALUES(?, ?, ?)";

        try {
            insert(ADD_TO_FRIENDS_QUERY, userId, friendId, "Пользователь добавлен в друзья");
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
    public List<User> getCommonFriends(Integer userId, Integer otherUserId) {
        final String GET_COMMON_FRIENDS =
                "SELECT f1.friend_id " +
                        "FROM friendship as f1 " +
                        "JOIN friendship as f2 ON f1.friend_id = f2.friend_id " +
                        "WHERE f1.user_id = ? " +
                        "AND f2.user_id = ?";
        return findMany(GET_COMMON_FRIENDS, userId, otherUserId);
    }

    @Override
    public List<User> friendGet(Integer userId) {
        final String FRIEND_GET =
                "SELECT friend_id " +
                        "FROM friendship " +
                        "WHERE user_id = ?";
        try {
            return findMany(FRIEND_GET, userId);
        } catch (RuntimeException e) {
            e.getStackTrace();
            System.out.println(Arrays.toString(e.getStackTrace()));
        }

        return findMany(FRIEND_GET, userId);
    }
}
