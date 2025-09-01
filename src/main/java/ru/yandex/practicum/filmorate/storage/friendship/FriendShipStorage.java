package ru.yandex.practicum.filmorate.storage.friendship;

import org.springframework.stereotype.Component;

@Component
public interface FriendShipStorage {
    void addFriend(Integer userId, Integer friendId);

    void removeUserFromFriends(Integer id, Integer friendId);
}
