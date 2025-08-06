package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.dtos.UserDto;
import ru.yandex.practicum.filmorate.mapper.UserMapper;
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

    public List<UserDto> sendFriendShipRequest(Long user_id, Long friend_id) {
        return friendShipStorage.sendFriendShipRequest(user_id, friend_id).stream()
                .map(UserMapper::mapUserToDto)
                .toList();
    }

    public List<UserDto> approveFriendShipRequest(Long user_id, Long friend_id) {
        return friendShipStorage.approveFriendShipRequest(user_id, friend_id).stream()
                .map(UserMapper::mapUserToDto)
                .toList();
    }

    public List<UserDto> refuseFriendShipRequest(Long user_id, Long friend_id) {
        return friendShipStorage.refuseFriendShipRequest(user_id, friend_id).stream()
                .map(UserMapper::mapUserToDto)
                .toList();
    }
}
