package ru.yandex.practicum.filmorate.storage.friendship;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

@Component
public interface FriendShipStorage {
    List<User> sendFriendShipRequest(Long user_id, Long friend_id);

    List<User> approveFriendShipRequest(Long user_id, Long friend_id);

    List<User> refuseFriendShipRequest(Long user_id, Long friend_id);
}
