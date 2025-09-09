package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Genres {
    private Integer filmId;
    private Integer genreId;
}
