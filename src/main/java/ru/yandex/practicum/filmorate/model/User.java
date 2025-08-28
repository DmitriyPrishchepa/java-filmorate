package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class User {
    private Integer id;
    @Email
    private String email;
    @NotBlank
    @Pattern(regexp = "^\\S*$", message = "Логин не должен содержать пробелов")
    private String login;
    private String name;
    @PastOrPresent
    private LocalDate birthday;
    private final List<User> friends = new ArrayList<>();
}