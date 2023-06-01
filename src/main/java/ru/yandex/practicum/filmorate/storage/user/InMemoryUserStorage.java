package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ObjectAlreadyExistException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.utils.IdGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static ru.yandex.practicum.filmorate.validator.Validator.validate;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {

    private final HashMap<Integer, User> users = new HashMap<>();

    @Override
    public List<User> getAll() {
        log.debug("Текущее количество пользователей: {}", users.size());
        return new ArrayList<>(users.values());
    }

    @Override
    public User create(User user) {
        validate(user);
        user.setId(IdGenerator.INSTANCE.generateId(User.class));
        checkEmail(user.getEmail());
        users.put(user.getId(), user);
        log.info("Пользователь добавлен.");
        return user;
    }

    @Override
    public User update(User user) {
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

    @Override
    public User delete(User user) {
        return users.remove(user.getId());
    }

    @Override
    public User getById(int id) {
        if (users.containsKey(id)) {
            log.info("Вывод пользователя по ID " + id);
            return users.get(id);
        } else {
            throw new NotFoundException("Пользователь по ID " + id + " не найден!");
        }
    }

    private void checkEmail(String email) {
        for (User user : users.values()) {
            if (user.getEmail().equals(email)) {
                throw new ObjectAlreadyExistException("Email уже занят.");
            }
        }
    }

}
