package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dal.BaseRepository;
import ru.yandex.practicum.filmorate.exception.exeptions.ElementNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.util.LocalDateToTimeStamp;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;

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
                    timestamp,
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
    public User getUserById(Integer id) {
        final String FIND_BY_ID_QUERY = "SELECT * FROM users WHERE id = ?";
        return findOne(FIND_BY_ID_QUERY, id)
                .orElseThrow(() -> new ElementNotFoundException("User not found"));
    }
}
