package ru.yandex.practicum.filmorate.storage.friendship;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.FriendShip;

import java.util.List;

@Component
public interface FriendShipStorage {
    void addFriend(Integer userId, Integer friendId);

    void removeUserFromFriends(Integer id, Integer friendId);

    List<FriendShip> getAllFriendship();
}
