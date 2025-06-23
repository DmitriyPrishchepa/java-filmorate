package ru.yandex.practicum.filmorate;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class FilmorateUsersTests {
    private Validator validator;
    String messageException;
    User testUser;
    UserStorage userStorage = new InMemoryUserStorage();
    UserService userService = new UserService(userStorage);
    UserController userController = new UserController(userService);

    @BeforeEach
    public void beforeEach() {
        testUser = User.builder()
                .name("newUser")
                .email("user@mail.ru")
                .login("userLogin")
                .birthday(LocalDate.of(1994, 10, 11))
                .build();

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    public void readException() {
        Set<ConstraintViolation<User>> violations = validator.validate(testUser);
        for (ConstraintViolation<User> viol : violations) {
            messageException = viol.getMessage();
        }
    }

    @Test
    public void shouldReturnListOfUsers() {
        userController.addUser(testUser);
        assertTrue(userController.findAllFilms().contains(testUser));
    }

    @Test
    void shouldThrowExceptionIfEmailIsNotValid() {
        testUser.setEmail("ававаав");

        readException();

        assertEquals("должно иметь формат адреса электронной почты", messageException);
    }

    @Test
    void shouldThrowExceptionIfLoginIsBlank() {
        testUser.setLogin("");
        readException();
        assertEquals("не должно быть пустым", messageException);
    }

    @Test
    void shouldThrowExceptionIfBirthdayInFuture() {
        testUser.setBirthday(LocalDate.of(2026, 10, 5));
        readException();
        assertEquals("должно содержать прошедшую дату или сегодняшнее число", messageException);
    }
}
