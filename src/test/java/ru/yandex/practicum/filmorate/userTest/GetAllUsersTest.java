package ru.yandex.practicum.filmorate.userTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.BaseInstructions;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GetAllUsersTest extends BaseInstructions {

    @BeforeEach
    void cleanTables() {
        cleanAllTables();
    }

    @Test
    void getAll() {

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

        List<User> users = userService.getAllUsers().stream().toList();
        assertThat(users).asList().size().isEqualTo(2);
    }
}
