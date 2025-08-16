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

    User updateUser(User user);

    Optional<User> getUserById(Integer id);

    void addFriend(Integer userId, Integer friendId);

    void removeUserFromFriends(Long id, Long friendId);

    List<User> getAllFriends(Integer id);

    List<User> getCommonFriends(Long userId, Long otherUserId);

    boolean existsById(int userId);
}