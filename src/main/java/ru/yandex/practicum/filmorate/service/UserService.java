package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.dtos.UserDto;
import ru.yandex.practicum.filmorate.dto.requests.user_requests.NewUserRequest;
import ru.yandex.practicum.filmorate.dto.requests.user_requests.UpdateUserRequest;
import ru.yandex.practicum.filmorate.exception.exeptions.ElementNotFoundException;
import ru.yandex.practicum.filmorate.mapper.UserMapper;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.List;

@Service
public class UserService {

    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public Collection<UserDto> getAllUsers() {
        return userStorage.getAllUsers()
                .stream()
                .map(UserMapper::mapUserToDto)
                .toList();
    }

    public UserDto addUser(NewUserRequest request) {
        User user = UserMapper.mapToUser(request);
        user = userStorage.addUser(user);
        return UserMapper.mapUserToDto(user);
    }

    public UserDto updateUser(long userId, UpdateUserRequest request) {
        User updatedUser = userStorage.getUserById(userId)
                .map(user -> UserMapper.updateUserFields(user, request))
                .orElseThrow(() -> new ElementNotFoundException("User not found"));

        updatedUser = userStorage.updateUser(updatedUser);
        return UserMapper.mapUserToDto(updatedUser);
    }

    public UserDto getUserById(Long id) {
        return userStorage.getUserById(id)
                .map(UserMapper::mapUserToDto)
                .orElseThrow(() -> new ElementNotFoundException("User not found"));
    }

    public void removeUserFromFriends(Long userId, Long friendId) {
        userStorage.removeUserFromFriends(userId, friendId);
    }

    public List<UserDto> getAllFriends(Long id) {
        return userStorage.getAllFriends(id).stream()
                .map(UserMapper::mapUserToDto)
                .toList();
    }

    public List<UserDto> getCommonFriends(Long userId, Long otherUserId) {
        return userStorage.getCommonFriends(userId, otherUserId).stream()
                .map(UserMapper::mapUserToDto)
                .toList();
    }
}
