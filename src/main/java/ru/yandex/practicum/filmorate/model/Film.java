package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.validation.ReleaseDateValidation;

import java.time.LocalDate;
import java.util.Set;

import static ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage.RELEASE_DATE_BEFORE;

@Data
@Builder
public class Film {
    @Min(1L)
    private Long id;
    @NotBlank
    private String name;
    @Size(max = 200)
    private String description;
    @ReleaseDateValidation(RELEASE_DATE_BEFORE)
    private LocalDate releaseDate;
    @Positive
    private Integer duration;
    private Set<Long> usersIdsLiked;
    @PositiveOrZero
    private Long likes;
}