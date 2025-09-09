package ru.yandex.practicum.filmorate.userTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.BaseInstructions;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.List;

public class UpdateUserTest extends BaseInstructions {

    @BeforeEach
    void cleanTables() {
        cleanAllTables();
    }

    @Test
    void updateUser() {

        createUserForTest(
                "Alex",
                "alex@gmail.com",
                "alexandr",
                LocalDate.of(1995, 10, 10)
        );

        List<User> allUsers = userService.getAllUsers().stream().toList();

        User userFromDb = allUsers.getFirst();

        userFromDb.setName("Dmitriy");

        updateUserForTest(userFromDb);

        User newUserFromDb = userService.getAllUsers().stream().toList().getFirst();
    }
}
