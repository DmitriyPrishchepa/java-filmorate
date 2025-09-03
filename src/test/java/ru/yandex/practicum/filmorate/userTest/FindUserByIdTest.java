package ru.yandex.practicum.filmorate.userTest;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FindUserByIdTest {

    private final UserDbStorage userStorage;

    @Test
    void findById() {
        userStorage.addUser(User.builder()
                .name("Alex")
                .email("alex@gmail.com")
                .login("alexandr")
                .birthday(LocalDate.of(1995, 10, 10))
                .build());

        Optional<User> userOptional = userStorage.getUserById(1);

        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user -> {
                    assertThat(user).hasFieldOrPropertyWithValue("email", "alex@gmail.com");
                });
    }
}
