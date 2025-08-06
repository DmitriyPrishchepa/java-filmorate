package ru.yandex.practicum.filmorate.dto.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GenresDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long filmId;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long genreId;
}
