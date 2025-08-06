package ru.yandex.practicum.filmorate.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dto.dtos.FriendShipDto;
import ru.yandex.practicum.filmorate.model.FriendShip;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FriendShipMapper {

    public static FriendShipDto mapToFriendShipDto(FriendShip friendShip) {
        return FriendShipDto.builder()
                .userId(friendShip.getUserId())
                .friendId(friendShip.getFriendId())
                .status(friendShip.getStatus())
                .build();
    }

    public static FriendShip mapToFriendShip(FriendShipDto friendShipDto) {
        return FriendShip.builder()
                .userId(friendShipDto.getUserId())
                .friendId(friendShipDto.getFriendId())
                .status(friendShipDto.getStatus())
                .build();
    }
}
