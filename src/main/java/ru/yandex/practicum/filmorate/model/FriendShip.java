package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FriendShip {
    private long userId;
    private long friendId;
    private String status;
}
