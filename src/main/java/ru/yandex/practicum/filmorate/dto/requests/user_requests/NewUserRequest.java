package ru.yandex.practicum.filmorate.dto.requests.user_requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;

@Data
public class NewUserRequest {
    private String name;
    @Email
    private String email;
    @Pattern(regexp = "^\\S*$", message = "Логин не должен содержать пробелов")
    private String login;
    @PastOrPresent
    private LocalDate birthday;
}
