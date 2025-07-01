package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
public class User {
    @Min(1L)
    private Long id;
    @Email
    private String email;
    @NotBlank
    private String login;
    private String name;
    @PastOrPresent
    private LocalDate birthday;
    @
    private Set<Long> friends;
}