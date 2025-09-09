package ru.yandex.practicum.filmorate.userTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.BaseInstructions;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CreateUserTest extends BaseInstructions {

    @BeforeEach
    void cleanTables() {
        cleanAllTables();
    }

    @Test
    public void testAddUser() {

        createUserForTest(
                "Alex",
                "alex@gmail.com",
                "alexandr",
                LocalDate.of(1995, 10, 10)
        );

        List<User> users = userService.getAllUsers().stream().toList();
        assertThat(users).asList().first().hasFieldOrPropertyWithValue("login", "alexandr");
    }
}
