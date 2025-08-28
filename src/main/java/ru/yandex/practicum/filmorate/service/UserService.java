package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.exeptions.DuplicateException;
import ru.yandex.practicum.filmorate.exception.exeptions.ElementNotFoundException;
import ru.yandex.practicum.filmorate.model.FriendShip;
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
        return userStorage.getUserById(id)
                .orElseThrow(() -> new ElementNotFoundException("User not found"));
    }

    public FriendShip addFriend(Integer userId, Integer friendId) {

        if (userId.equals(friendId)) {
            log.debug("Пользователь {} пытается добавить самого себя в друзья", userId);
            throw new DuplicateException("Нельзя добавить самого себя в друзья");
        }

        User user = getUserById(userId);
        User friend = getUserById(friendId);

        if (user.getFriends().contains(friend)) {
            log.debug("Пользователь {} пытается добавить {} в друзья дважды", userId, friendId);
            throw new DuplicateException("Нельзя добавить в друзья дважды");
        }

        userStorage.addFriend(userId, friendId);

        user.getFriends().add(friend);

        return FriendShip.builder()
                .user(user)
                .friend(friend)
                .status("Пользователь " + friend.getName() + " у добавлен в друзья")
                .build();
    }


    public void removeUserFromFriends(Integer userId, Integer friendId) {
        if (userId.equals(friendId)) {
            log.debug("Пользователь {} пытается удалить самого себя из друзей", userId);
            throw new DuplicateException("Нельзя удалить самого себя из друзей");
        }

        User user = getUserById(userId);
        User friend = getUserById(friendId);

        if (!user.getFriends().remove(friend.getId())) {
            log.debug("Дружба не найдена");
            return;
        }

        userStorage.removeUserFromFriends(userId, friendId);
    }

    public List<User> getCommonFriends(Integer userId, Integer otherUserId) {
        return userStorage.getCommonFriends(userId, otherUserId);
    }

    public List<User> friendGet(Integer userId) {
        User user = getUserById(userId);
        return userStorage.friendGet(user.getId());
    }
}

