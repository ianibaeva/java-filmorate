package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ObjectAlreadyExistException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.utils.IdGenerator;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static ru.yandex.practicum.filmorate.validator.Validator.validate;

@Slf4j
@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final HashMap<Integer, User> users = new HashMap<>();

    @GetMapping
    public List<User> getUsers() {
        log.debug("Текущее количество пользователей: {}", users.size());
        return new ArrayList<>(users.values());
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) throws ValidationException {
        validate(user);
        user.setId(IdGenerator.INSTANCE.generateId(User.class));
        checkEmail(user.getEmail());
        users.put(user.getId(), user);
        log.info("Пользователь добавлен.");
        return user;
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        validate(user);
        if (users.get(user.getId()) != null) {
            users.put(user.getId(), user);
            log.info("Пользователь изменён.");
        } else {
            log.debug("Пользователь по ID " + user.getId() + " не найден.");
            throw new NotFoundException("Пользователь по ID " + user.getId() + " не найден.");
        }
        return user;
    }

    private void checkEmail(String email) throws ValidationException {
        for (User user : users.values()) {
            if (user.getEmail().equals(email)) {
                throw new ObjectAlreadyExistException("Email уже занят.");
            }
        }
    }

}
