package ru.yandex.practicum.filmorate.dto.requests.films_requests;

import lombok.Data;

import java.time.LocalDate;

@Data
public class NewFilmRequest {
    private String name;
    private String description;
    private LocalDate releaseDate;
    private Integer duration;
    private Integer mpaId;


}
