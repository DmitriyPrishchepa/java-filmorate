package ru.yandex.practicum.filmorate.userTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.BaseInstructions;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class RemoveFromFriendsTest extends BaseInstructions {

    @BeforeEach
    void cleanTables() {
        cleanAllTables();
    }

    @Test
    void removeFriend() {

        createUserForTest(
                "Alex",
                "alex@gmail.com",
                "alexandr",
                LocalDate.of(1995, 10, 10)
        );

        createUserForTest(
                "Dima",
                "dimchik@gmail.com",
                "dmitr",
                LocalDate.of(2000, 10, 10)
        );

        createUserForTest(
                "Igor",
                "igor@gmail.com",
                "iggir",
                LocalDate.of(1999, 12, 5)
        );

        friendShipService.addFriend(1, 2);
        friendShipService.addFriend(1, 3);

        friendShipService.removeUserFromFriends(1, 3);

        List<User> friends = userService.friendGet(1);
        assertThat(friends).asList().size().isEqualTo(1);
    }
}
