package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dal.BaseRepository;
import ru.yandex.practicum.filmorate.model.User;

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

        try {

            Integer id = insert(
                    INSERT_QUERY,
                    user.getName(),
                    user.getEmail(),
                    user.getLogin(),
                    user.getBirthday()
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

        try {
            update(
                    UPDATE_QUERY,
                    user.getName(),
                    user.getEmail(),
                    user.getLogin(),
                    user.getBirthday(),
                    user.getId()
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
    public List<User> getCommonFriends(Integer userId, Integer otherUserId) {

        final String GET_COMMON_FRIENDS =
                "SELECT * FROM users u " +
                        "JOIN friendship f1 ON u.id = f1.friend_id " +
                        "JOIN friendship f2 ON u.id = f2.friend_id " +
                        "WHERE f1.user_id = ? AND f2.user_id = ?";
        return findMany(GET_COMMON_FRIENDS, userId, otherUserId);
    }

    @Override
    public List<User> friendGet(Integer userId) {
        final String FRIEND_GET =
                "SELECT * FROM users u " +
                        "JOIN friendship f " +
                        "ON u.id = f.friend_id " +
                        "WHERE f.user_id = ?";
        return findMany(FRIEND_GET, userId);
    }
}
