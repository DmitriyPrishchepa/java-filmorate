package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.friendship.FriendShipDbStorage;
import ru.yandex.practicum.filmorate.storage.friendship.FriendShipStorage;

import java.util.List;

@Service
public class FriendShipService {

    private final FriendShipStorage friendShipStorage;

    @Autowired
    public FriendShipService(FriendShipDbStorage friendShipStorage) {
        this.friendShipStorage = friendShipStorage;
    }

    public List<User> sendFriendShipRequest(Long user_id, Long friend_id) {
        return friendShipStorage.sendFriendShipRequest(user_id, friend_id);
    }

    public List<User> approveFriendShipRequest(Long user_id, Long friend_id) {
        return friendShipStorage.approveFriendShipRequest(user_id, friend_id);
    }

    public List<User> refuseFriendShipRequest(Long user_id, Long friend_id) {
        return friendShipStorage.refuseFriendShipRequest(user_id, friend_id);
    }
}
