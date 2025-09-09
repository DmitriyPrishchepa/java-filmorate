package ru.yandex.practicum.filmorate.userTest;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.BaseInstructions;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
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

        List<User> allUsers = userService.getAllUsers().stream().toList();

        for (User user : allUsers) {
            log.debug("user {}", user);
        }

        User userAlex = allUsers.getFirst();
        User userDima = allUsers.get(1);
        User igorUser = allUsers.getLast();

        friendShipService.addFriend(userAlex.getId(), userDima.getId());
        friendShipService.addFriend(igorUser.getId(), userDima.getId());

        List<User> commonFriends = userService.getCommonFriends(userAlex.getId(), igorUser.getId());

        assertThat(commonFriends).asList().contains(userDima);
    }
}
