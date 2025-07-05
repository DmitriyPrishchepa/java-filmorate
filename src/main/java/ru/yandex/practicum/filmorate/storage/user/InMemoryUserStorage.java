package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.exeptions.ElementNotFoundException;
import ru.yandex.practicum.filmorate.exception.exeptions.ValidateLoginIncorrectException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {

    public final Map<Long, User> users = new HashMap<>();

    @Override
    public Collection<User> getAllUsers() {
        log.info("Getting users...");
        return users.values();
    }

    @Override
    public User addUser(User user) {

        log.info("Start addition of users...");

        if (user.getLogin().contains(" ")) {
            log.warn("Warning: Incorrect user login {}", user.getLogin());
            throw new ValidateLoginIncorrectException("User email can not contains white space symbols");
        }

        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }

        user.setId(getNexId());
        user.setFriends(new HashSet<>());
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User getUserById(Long id) {
        return users.get(id);
    }

    @Override
    public Optional<User> updateUser(User newUser) {
        log.trace("Вызван метод updateUser");
        newUser.setFriends(new HashSet<>());

        if (getUserById(newUser.getId()) == null) {
            throw new ElementNotFoundException("User unknown");
        }

        return Optional.ofNullable(users.computeIfPresent(newUser.getId(), (k, v) -> newUser));
    }

    @Override
    public Collection<User> addUserToFriends(Long userId, Long friendId) {

        User user = getUserById(userId);
        User friend = getUserById(friendId);

        Set<Long> friendFriends = friend.getFriends();
        Set<Long> friendNewFriends = new HashSet<>(friendFriends);
        friendNewFriends.add(userId);
        friend.setFriends(friendNewFriends);

        Set<Long> userFriends = user.getFriends();
        Set<Long> newFriends = new HashSet<>(userFriends);
        newFriends.add(friendId);
        user.setFriends(newFriends);

        Map<Long, User> usersFriends = users.entrySet().stream()
                .filter(entry -> user.getFriends().contains(entry.getKey()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));

        return usersFriends.values();
    }

    @Override
    public void removeUserFromFriends(Long userId, Long friendId) {

        User user = getUserById(userId);
        User friend = getUserById(friendId);

        Set<Long> friendFriends = friend.getFriends();
        Set<Long> friendNewFriends = new HashSet<>(friendFriends);
        friendNewFriends.remove(userId);
        friend.setFriends(friendNewFriends);

        Set<Long> userFriends = user.getFriends();
        Set<Long> newFriends = new HashSet<>(userFriends);
        newFriends.remove(friendId);
        user.setFriends(newFriends);
    }


    @Override
    public ArrayList<User> getAllFriends(Long id) {

        User user = getUserById(id);

        Collection<Long> friendsIds = user.getFriends();

        Map<Long, User> filteredUsers = users.entrySet().stream()
                .filter(entry -> friendsIds.contains(entry.getKey()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));

        return new ArrayList<>(filteredUsers.values());
    }

    @Override
    public List<User> getCommonFriends(Long userId, Long otherUserId) {

        User user = getUserById(userId);
        User otherUser = getUserById(otherUserId);

        Collection<Long> userFriends = user.getFriends();
        Collection<Long> otherUserFriends = otherUser.getFriends();

        Set<Long> commonFriendsIds = userFriends.stream()
                .filter(otherUserFriends::contains)
                .collect(Collectors.toSet());

        return users.values().stream()
                .filter(user1 -> commonFriendsIds.contains(user1.getId()))
                .toList();
    }

    @Override
    public Long getNexId() {
        long currentMaxId = users.values().stream()
                .mapToLong(User::getId)
                .max()
                .orElse(0);
        log.debug("currentUserMaxId {}", currentMaxId);
        return ++currentMaxId;
    }
}
