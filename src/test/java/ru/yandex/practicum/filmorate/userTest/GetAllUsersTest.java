package ru.yandex.practicum.filmorate.userTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.BaseInstructions;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GetAllUsersTest extends BaseInstructions {

    @BeforeEach
    void cleanTables() {
        cleanAllTables();
    }

    @Test
    void getAll() {

        addSomeUsersBatchUpdate(users);

        List<User> users = userService.getAllUsers().stream().toList();

        assertThat(users).asList().size().isEqualTo(3);
    }
}
