package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FriendShipService;

import java.util.List;

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

    @GetMapping("/send-request")
    public List<User> sendRequest(@RequestParam(value = "user_id") Long user_id,
                                  @RequestParam(value = "friend_id") Long friend_id) {
        return friendShipService.sendFriendShipRequest(user_id, friend_id);
    }

    @GetMapping("/approve-request")
    public List<User> approveRequest(@RequestParam(value = "user_id") Long user_id,
                                     @RequestParam(value = "friend_id") Long friend_id) {
        return friendShipService.approveFriendShipRequest(user_id, friend_id);
    }

    @GetMapping("/refuse-request")
    public List<User> refuseRequest(@RequestParam(value = "user_id") Long user_id,
                                    @RequestParam(value = "friend_id") Long friend_id) {
        return friendShipService.refuseFriendShipRequest(user_id, friend_id);
    }
}
