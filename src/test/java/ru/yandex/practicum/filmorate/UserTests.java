package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.friendship.FriendShipDbStorage;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UserTests {

    private final UserDbStorage userStorage;
    private final FriendShipDbStorage friendshipStorage;
    private final FilmDbStorage filmDbStorage;

    @Test
    public void testAddUser() {
        User user = userStorage.addUser(User.builder()
                .name("Alex")
                .email("alex@gmail.com")
                .login("alexandr")
                .birthday(LocalDate.of(1995, 10, 10))
                .build());

        assertThat(user).hasFieldOrPropertyWithValue("id", 1);
    }

    @Test
    public void testUpdateUser() {
        User user = userStorage.updateUser(User.builder()
                .name("Tim")
                .build());

        assertThat(user).hasFieldOrPropertyWithValue("name", "Tim");
    }

    @Test
    public void testFindUserById() {
        Optional<User> userOptional = userStorage.getUserById(1);

        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("id", 1)
                );
    }

    @Test
    public void testGetAllUsers() {
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

        List<User> users = userStorage.getAllUsers().stream().toList();

        assertThat(users).asList().size().isEqualTo(6);
        assertThat(users).asList().size().isNotEqualTo(4);
    }

    @Test
    public void testAddAndGetFriends() {
        friendshipStorage.addFriend(1, 2);
        friendshipStorage.addFriend(1, 3);
        List<User> friends = userStorage.friendGet(1);
        assertThat(friends).asList().size().isEqualTo(2);
        assertThat(friends).asList().size().isNotEqualTo(3);
    }

    @Test
    public void testGetCommonFriends() {
        User user1 = userStorage.addUser(User.builder()
                .name("Svyatoslav")
                .email("svyat@gmail.com")
                .login("svyatoy")
                .birthday(LocalDate.of(1992, 5, 11))
                .build());

        User user2 = userStorage.addUser(User.builder()
                .name("Oleg")
                .email("olli@gmail.com")
                .login("olegek")
                .birthday(LocalDate.of(1991, 5, 11))
                .build());

        User user3 = userStorage.addUser(User.builder()
                .name("Jogn")
                .email("jonny@gmail.com")
                .login("j")
                .birthday(LocalDate.of(1991, 10, 15))
                .build());

        Integer user1Id = user1.getId();
        Integer user2Id = user2.getId();
        Integer user3Id = user3.getId();

        friendshipStorage.addFriend(user1Id, user2Id);
        friendshipStorage.addFriend(user3Id, user2Id);

        List<User> commonFriends = userStorage.getCommonFriends(user1.getId(), user3.getId());

        assertThat(commonFriends).asList().contains(user2);
    }

    @Test
    public void testRemoveUserFromFriends() {
        List<User> friendsOfUser2 = userStorage.friendGet(2);
        Optional<User> optionalUser = userStorage.getUserById(3);

        assertThat(friendsOfUser2).asList().size().isEqualTo(1);

        optionalUser.ifPresent(user -> assertThat(friendsOfUser2).asList().contains(user));

        optionalUser.ifPresent(user -> friendshipStorage.removeUserFromFriends(2, user.getId()));

        List<User> friendsOfUser = userStorage.friendGet(2);
        assertThat(friendsOfUser).asList().isEmpty();
    }
}
