package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<User> getUsers() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {
        return userService.getById(id);
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        return userService.create(user);
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        return userService.update(user);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public User addToFriends(@PathVariable int id, @PathVariable int friendId) {
        return userService.addFriend(userService.getById(id), userService.getById(friendId));
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public User removeFromFriends(@PathVariable int id, @PathVariable int friendId) {
        return userService.removeFriend(userService.getById(id), userService.getById(friendId));
    }

    @GetMapping("/{id}/friends")
    public List<User> getFriends(@PathVariable int id) {
        return userService.getFriends(userService.getById(id));
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getMutualFriends(@PathVariable int id, @PathVariable int otherId) {
        return userService.getMutualFriends(id, otherId);
    }

}
