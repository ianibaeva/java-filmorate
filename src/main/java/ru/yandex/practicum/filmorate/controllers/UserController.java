package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService service) {
        this.userService = service;
    }

    @GetMapping
    public List<User> getAll() {
        log.info("Получен GET запрос /users.");
        return userService.getAll();
    }

    @GetMapping(value = "/{id}")
    public User getById(@PathVariable int id) {
        log.info("Получен GET запрос /users/{}.", id);
        return userService.getById(id);
    }

    @PostMapping
    public User create(@RequestBody @Valid User user) {
        log.info("Получен POST запрос /users. Передано: {}", user);
        return userService.create(user);
    }

    @PutMapping
    public User update(@RequestBody @Valid User user) {
        log.info("Получен PUT запрос /users. Передано: {}", user);
        return userService.update(user);
    }

    @PutMapping(value = "/{id}/friends/{friendId}")
    public void addFriend(@PathVariable int id, @PathVariable int friendId) {
        log.info("Получен PUT запрос /users/{}/friends/{}.", id, friendId);
        userService.addFriend(id, friendId);
    }

    @DeleteMapping(value = "/{id}/friends/{friendId}")
    public void removeFriend(@PathVariable int id, @PathVariable int friendId) {
        log.info("Получен DELETE запрос /users/{}/friends/{}.", id, friendId);
        userService.removeFriend(id, friendId);
    }

    @GetMapping(value = "/{id}/friends")
    public List<User> getFriends(@PathVariable int id) {
        log.info("Получен GET запрос /users/{}/friends.", id);
        return userService.getFriends(id);
    }

    @GetMapping(value = "/{id}/friends/common/{otherId}")
    public List<User> getMutualFriends(@PathVariable int id, @PathVariable int otherId) {
        log.info("Получен GET запрос /users/{}/friends/common/{}", id, otherId);
        return userService.getMutualFriends(id, otherId);
    }
}
