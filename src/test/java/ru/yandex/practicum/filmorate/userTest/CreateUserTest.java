package ru.yandex.practicum.filmorate.userTest;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CreateUserTest {

    private final UserDbStorage userStorage;

    @Test
    public void testAddUser() {

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

        User user = userStorage.addUser(User.builder()
                .name("Igor")
                .email("igor@gmail.com")
                .login("iggir")
                .birthday(LocalDate.of(1999, 12, 5))
                .build());

        userStorage.addUser(User.builder()
                .name("Svyatoslav")
                .email("svyat@gmail.com")
                .login("svyatoy")
                .birthday(LocalDate.of(1992, 5, 11))
                .build());

        userStorage.addUser(User.builder()
                .name("Oleg")
                .email("olli@gmail.com")
                .login("olegek")
                .birthday(LocalDate.of(1991, 5, 11))
                .build());

        userStorage.addUser(User.builder()
                .name("Jogn")
                .email("jonny@gmail.com")
                .login("j")
                .birthday(LocalDate.of(1991, 10, 15))
                .build());

        assertThat(user).hasFieldOrPropertyWithValue("id", 3);

        userStorage.removeAllUsers();
    }
}
