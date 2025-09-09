package ru.yandex.practicum.filmorate.userTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.BaseInstructions;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GetCommonFriendsTest extends BaseInstructions {

    @BeforeEach
    void cleanTables() {
        cleanAllTables();
    }

    @Test
    public void testGetCommonFriends() {

        addSomeUsersBatchUpdate(users);

        List<User> allUsers = userService.getAllUsers().stream().toList();

        User userAlex = allUsers.getFirst();
        User userDima = allUsers.get(1);
        User igorUser = allUsers.getLast();

        friendShipService.addFriend(userAlex.getId(), userDima.getId());
        friendShipService.addFriend(igorUser.getId(), userDima.getId());

        List<User> commonFriends = userService.getCommonFriends(userAlex.getId(), igorUser.getId());

        assertThat(commonFriends).asList().contains(userDima);
    }
}
