package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.FriendShip;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
@Validated
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<User> findAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@Valid @RequestBody User user) {
        return userService.addUser(user);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public User updateUser(@Valid @RequestBody User user) {
        return userService.updateUser(user);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") @Positive Integer id) {
        return userService.getUserById(id);
    }

    @GetMapping("/{userId}/friends")
    @ResponseStatus(HttpStatus.OK)
    public List<User> friendGet(@PathVariable("userId") Integer userId) {
        return userService.friendGet(userId);
    }

    @DeleteMapping("/{userId}/friends/{friendId}")
    public void removeUserFromFriends(
            @PathVariable Integer userId,
            @PathVariable Integer friendId
    ) {
        userService.removeUserFromFriends(userId, friendId);
    }

    @GetMapping("/{userId}/friends/common/{otherUserId}")
    public List<User> getCommonFriends(
            @PathVariable("userId") Integer userId,
            @PathVariable("otherUserId") Integer otherUserId) {
        return userService.getCommonFriends(userId, otherUserId);
    }

    @PutMapping("/{userId}/friends/{friendId}")
    @ResponseStatus(HttpStatus.OK)
    public FriendShip addFriend(
            @PathVariable("userId") Integer userId,
            @PathVariable("friendId") Integer friendId
    ) {
        return userService.addFriend(userId, friendId);
    }
}