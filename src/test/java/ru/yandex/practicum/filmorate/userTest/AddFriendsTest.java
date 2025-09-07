package ru.yandex.practicum.filmorate.userTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.BaseInstructions;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AddFriendsTest extends BaseInstructions {

    @BeforeEach
    void cleanTables() {
        cleanAllTables();
    }

    @Test
    void addToFriends() {

        userService.addUser(User.builder()
                .name("Alex")
                .email("alex@gmail.com")
                .login("alexandr")
                .birthday(LocalDate.of(1995, 10, 10))
                .build());

        userService.addUser(User.builder()
                .name("Dima")
                .email("dimchik@gmail.com")
                .login("dmitr")
                .birthday(LocalDate.of(2000, 10, 10))
                .build());

        userService.addUser(User.builder()
                .name("Igor")
                .email("igor@gmail.com")
                .login("iggir")
                .birthday(LocalDate.of(1999, 12, 5))
                .build());

        friendShipService.addFriend(1, 2);
        friendShipService.addFriend(1, 3);

        List<User> friends = userService.friendGet(1);

        assertThat(friends).asList().size().isEqualTo(2);
        assertThat(friends).asList().size().isNotEqualTo(3);
    }
}
