package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.exeptions.DuplicateException;
import ru.yandex.practicum.filmorate.exception.exeptions.ElementNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.util.UserUpdater;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public Collection<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    public User addUser(User request) {
        return userStorage.addUser(request);
    }

    public User updateUser(User request) {
        User existingUser = getUserById(request.getId());
        User updatedUser = UserUpdater.updateFieldsOfUser(existingUser, request);
        return userStorage.updateUser(updatedUser);
    }

    public User getUserById(Integer id) {
        return userStorage.getUserById(id)
                .orElseThrow(() -> new ElementNotFoundException("User not found"));
    }

    public void removeUserFromFriends(Long userId, Long friendId) {
        userStorage.removeUserFromFriends(userId, friendId);
    }

    public List<User> getAllFriends(Integer id) {
        return userStorage.getAllFriends(id);
    }

    public List<User> getCommonFriends(Long userId, Long otherUserId) {
        return userStorage.getCommonFriends(userId, otherUserId);
    }

    public void addFriend(Integer userId, Integer friendId) {
        if (userId.equals(friendId)) {
            log.debug("Пользователь {} пытается добавить самого себя в друзья", userId);
            throw new DuplicateException("Нельзя добавить самого себя в друзья");
        }

        validateUserExists(userId);
        validateUserExists(friendId);

        User user = getUserById(userId);

        if (user.getFriends().contains(friendId)) {
            log.debug("Пользователь {} пытается добавить {} в друзья дважды", userId, friendId);
            throw new DuplicateException("Нельзя добавить в друзья дважды");
        }

        userStorage.addFriend(userId, friendId);

        user.getFriends().addAll(
                new LinkedHashSet<>(userStorage.getAllFriends(userId)
                        .stream()
                        .map(
                                User::getId
                        ).collect(Collectors.toSet())
                )
        );
        log.debug("Пользователь {} добавил {} в друзья", userId, friendId);
    }

    public void validateUserExists(Integer id) {
        if (!userStorage.existsById(id)) {
            throw new ElementNotFoundException("User not found");
        }
    }
}

