package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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
    private final Set<Integer> friends = new LinkedHashSet<>();
}