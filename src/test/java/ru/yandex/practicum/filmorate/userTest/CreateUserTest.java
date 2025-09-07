package ru.yandex.practicum.filmorate.userTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.BaseInstructions;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CreateUserTest extends BaseInstructions {

    @BeforeEach
    void cleanTables() {
        cleanAllTables();
    }

    @Test
    public void testAddUser() {

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

        User user = userService.addUser(User.builder()
                .name("Igor")
                .email("igor@gmail.com")
                .login("iggir")
                .birthday(LocalDate.of(1999, 12, 5))
                .build());

        userService.addUser(User.builder()
                .name("Svyatoslav")
                .email("svyat@gmail.com")
                .login("svyatoy")
                .birthday(LocalDate.of(1992, 5, 11))
                .build());

        userService.addUser(User.builder()
                .name("Oleg")
                .email("olli@gmail.com")
                .login("olegek")
                .birthday(LocalDate.of(1991, 5, 11))
                .build());

        userService.addUser(User.builder()
                .name("Jogn")
                .email("jonny@gmail.com")
                .login("j")
                .birthday(LocalDate.of(1991, 10, 15))
                .build());

        assertThat(user).hasFieldOrPropertyWithValue("id", 3);
    }
}
