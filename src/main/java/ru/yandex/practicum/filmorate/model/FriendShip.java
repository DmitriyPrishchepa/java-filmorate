package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FriendShip {
    private User user;
    private User friend;
    private String status;
}
