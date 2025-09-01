package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.util.UserUpdater;

import java.util.Collection;
import java.util.List;

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
        return userStorage.getUserById(id);
    }

    public List<User> getCommonFriends(Integer userId, Integer otherUserId) {
        return userStorage.getCommonFriends(userId, otherUserId);
    }

    public List<User> friendGet(Integer userId) {
        User user = getUserById(userId);
        return userStorage.friendGet(user.getId());
    }
}

