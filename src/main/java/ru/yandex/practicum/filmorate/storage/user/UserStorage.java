package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
public interface UserStorage {
    Collection<User> getAllUsers();

    User addUser(User user);

    Optional<User> updateUser(User user);

    Long getNexId();

    User getUserById(Long id);

    Collection<User> addUserToFriends(Long userId, Long friendId);

    void removeUserFromFriends(Long id, Long friendId);

    List<User> getAllFriends(Long id);

    List<User> getCommonFriends(Long userId, Long otherUserId);
}
