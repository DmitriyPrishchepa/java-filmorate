package ru.yandex.practicum.filmorate.dto.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FriendShipDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long userId;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long friendId;
    private String status;
}
