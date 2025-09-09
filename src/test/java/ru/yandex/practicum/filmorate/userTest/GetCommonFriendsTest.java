package ru.yandex.practicum.filmorate.userTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.BaseInstructions;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GetCommonFriendsTest extends BaseInstructions {

    @BeforeEach
    void cleanTables() {
        cleanAllTables();
    }

    @Test
    public void testGetCommonFriends() {


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
        friendShipService.addFriend(3, 2);

        List<User> commonFriends = userService.getCommonFriends(1, 3);

        User user = userService.getUserById(2);

        assertThat(commonFriends).asList().contains(user);
    }
}
