package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.exeptions.ElementNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.List;

@Component
public interface UserStorage {
    Collection<User> getAllUsers();

    User addUser(User user);

    User updateUser(User user);

    Long getNexId();

    User getUserById(Long id);

    Collection<User> addUserToFriends(Long userId, Long friendId);

    void removeUserFromFriends(Long id, Long friendId);

    List<User> getAllFriends(Long id);

    List<User> getCommonFriends(Long userId, Long otherUserId);

    void checkAreIdsEmpty(Long userId, Long friendId);
}
