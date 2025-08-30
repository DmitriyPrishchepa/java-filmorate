package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.friendship.FriendShipStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.util.UserUpdater;

import java.util.Collection;
import java.util.List;

@Service
@Slf4j
public class UserService {

    private final UserStorage userStorage;
    private FriendShipStorage friendShipStorage;

    @Autowired
    public UserService(UserStorage userStorage, FriendShipStorage friendShipStorage) {
        this.userStorage = userStorage;
        this.friendShipStorage = friendShipStorage;
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
        List<User> friends = friendShipStorage.friendGet(id)
                .stream()
                .map(friendShip -> {
                    return userStorage.getUserById(id);
                })
                .toList();

        User user = userStorage.getUserById(id);
        user.getFriends().addAll(friends);
        return user;
    }
}

