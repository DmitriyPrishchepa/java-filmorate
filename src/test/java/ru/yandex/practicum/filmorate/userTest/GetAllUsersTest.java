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
public class GetAllUsersTest extends BaseInstructions {

    @BeforeEach
    void cleanTables() {
        cleanAllTables();
    }

    @Test
    void getAll() {

        createUserForTest(
                "Alex",
                "alex@gmail.com",
                "alexandr",
                LocalDate.of(1995, 10, 10)
        );

        createUserForTest(
                "Dmitriy",
                "dima@gmail.com",
                "dm",
                LocalDate.of(1993, 11, 10)
        );


        List<User> users = userService.getAllUsers().stream().toList();

        log.debug("users {}", users.size());

        assertThat(users).asList().size().isEqualTo(2);
    }
}
