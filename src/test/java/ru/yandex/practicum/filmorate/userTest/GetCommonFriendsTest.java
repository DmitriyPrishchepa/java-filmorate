package ru.yandex.practicum.filmorate.userTest;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.friendship.FriendShipDbStorage;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GetCommonFriendsTest {

    private final UserDbStorage userStorage;
    private final FriendShipDbStorage friendShipDbStorage;

    @Test
    public void testGetCommonFriends() {

        userStorage.addUser(User.builder()
                .name("Alex")
                .email("alex@gmail.com")
                .login("alexandr")
                .birthday(LocalDate.of(1995, 10, 10))
                .build());

        userStorage.addUser(User.builder()
                .name("Dima")
                .email("dimchik@gmail.com")
                .login("dmitr")
                .birthday(LocalDate.of(2000, 10, 10))
                .build());

        userStorage.addUser(User.builder()
                .name("Igor")
                .email("igor@gmail.com")
                .login("iggir")
                .birthday(LocalDate.of(1999, 12, 5))
                .build());

        friendShipDbStorage.addFriend(1, 2);
        friendShipDbStorage.addFriend(3, 2);

        List<User> commonFriends = userStorage.getCommonFriends(1, 3);

        Optional<User> userOptional = userStorage.getUserById(2);

        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user -> {
                    assertThat(commonFriends).asList().contains(user);
                });

        friendShipDbStorage.clearFriendship();
        userStorage.removeAllUsers();
    }
}
