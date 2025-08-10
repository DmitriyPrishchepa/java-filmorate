package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.dtos.UserDto;
import ru.yandex.practicum.filmorate.dto.requests.user_requests.NewUserRequest;
import ru.yandex.practicum.filmorate.dto.requests.user_requests.UpdateUserRequest;
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
    public Collection<UserDto> findAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto addUser(@Valid @RequestBody NewUserRequest user) {
        return userService.addUser(user);
    }

    @PutMapping("/{id}")
    public UserDto updateUser(@PathVariable("id") long id, @Valid @RequestBody UpdateUserRequest user) {
        return userService.updateUser(id, user);
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable("id") @Positive Long id) {
        return userService.getUserById(id);
    }

    @DeleteMapping("/{userId}/friends/{friendId}")
    public void removeUserFromFriends(
            @PathVariable @Positive Long userId,
            @PathVariable @Positive Long friendId
    ) {
        userService.removeUserFromFriends(userId, friendId);
    }

    @GetMapping("/{id}/friends")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getAllFriends(@PathVariable("id") @Positive Long id) {
        return userService.getAllFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<UserDto> getCommonFriends(@PathVariable("id") @Positive Long id, @Positive @PathVariable("otherId") Long otherId) {
        return userService.getCommonFriends(id, otherId);
    }
}