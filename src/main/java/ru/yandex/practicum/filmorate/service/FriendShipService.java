package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.exeptions.DuplicateException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.friendship.FriendShipStorage;

import java.util.List;

@Service
@Slf4j
public class FriendShipService {

    private final FriendShipStorage friendShipStorage;
    private final UserService userService;

    @Autowired
    public FriendShipService(FriendShipStorage friendShipStorage, UserService userService) {
        this.friendShipStorage = friendShipStorage;
        this.userService = userService;
    }

    public void addFriend(Integer userId, Integer friendId) {

        userService.getUserById(userId);
        userService.getUserById(friendId);

        friendShipStorage.addFriend(userId, friendId);
    }


    public void removeUserFromFriends(Integer userId, Integer friendId) {

        if (userId.equals(friendId)) {
            log.debug("Пользователь {} пытается удалить самого себя из друзей", userId);
            throw new DuplicateException("Нельзя удалить самого себя из друзей");
        }

        userService.getUserById(userId);
        User friend = userService.getUserById(friendId);

        List<User> friends = userService.friendGet(userId);

        if (friends.contains(friend)) {
            friendShipStorage.removeUserFromFriends(userId, friendId);
        }
    }
}
