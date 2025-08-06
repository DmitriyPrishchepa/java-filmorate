package ru.yandex.practicum.filmorate.storage.friendship;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

@Component
public interface FriendShipStorage {
    Collection<User> sendFriendShipRequest(Long user_id, Long friend_id);

    Collection<User> approveFriendShipRequest(Long user_id, Long friend_id);

    Collection<User> refuseFriendShipRequest(Long user_id, Long friend_id);
}
