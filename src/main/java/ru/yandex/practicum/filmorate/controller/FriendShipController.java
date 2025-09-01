package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.service.FriendShipService;

@RestController
@RequestMapping("/users")
@Slf4j
@Validated
public class FriendShipController {

    private final FriendShipService friendShipService;

    @Autowired
    public FriendShipController(FriendShipService friendShipService) {
        this.friendShipService = friendShipService;
    }

    @PutMapping("/{userId}/friends/{friendId}")
    @ResponseStatus(HttpStatus.OK)
    public void addFriend(
            @PathVariable("userId") Integer userId,
            @PathVariable("friendId") Integer friendId
    ) {
        friendShipService.addFriend(userId, friendId);
    }

    @DeleteMapping("/{userId}/friends/{friendId}")
    public void removeUserFromFriends(
            @PathVariable Integer userId,
            @PathVariable Integer friendId
    ) {
        friendShipService.removeUserFromFriends(userId, friendId);
    }
}
