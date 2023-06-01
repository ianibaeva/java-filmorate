package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    private final UserStorage users;

    @Autowired
    public UserService(UserStorage users) {
        this.users = users;
    }

    public User create(User user) {
        return users.create(user);
    }

    public User update(User user) {
        return users.update(user);
    }

    public User delete(User user) {
        return users.delete(user);
    }

    public User getById(int id) {
        return users.getById(id);
    }

    public List<User> getAll() {
        return users.getAll();
    }

    public User addFriend(User user, User newFriend) {
        user.getFriendsId().add(newFriend.getId());
        newFriend.getFriendsId().add(user.getId());
        users.update(user);
        users.update(newFriend);
        log.info("Пользователь " + user.getName() + " добавил в друзья пользователя " + newFriend.getName());
        return user;
    }

    public User removeFriend(User user, User notFriend) {
        user.getFriendsId().remove(notFriend.getId());
        notFriend.getFriendsId().remove(user.getId());
        users.update(user);
        users.update(notFriend);
        log.info("Пользователь " + user.getName() + " удалил из друзей пользователя " + notFriend.getName());
        return user;
    }

    public List<User> getMutualFriends(int id, int otherId) {
        User user = getById(id);
        User friend = getById(otherId);
        List<Integer> mutualFriends = new ArrayList<>(user.getFriendsId());
        mutualFriends.retainAll(friend.getFriendsId());
        return mutualFriends.stream()
                .map(this::getById)
                .collect(Collectors.toList());
    }

    public List<User> getFriends(User user) {
        List<User> friends = new ArrayList<>();
        if (user.getFriendsId() == null) {
            log.info("Список друзей пуст.");
            return new ArrayList<>();
        }
        for (Integer id : user.getFriendsId()) {
            User friend = users.getById(id);
            if (friend != null) {
                friends.add(friend);
            }
        }
        log.info("Список друзей пользователя {}", user);
        return new ArrayList<>(friends);
    }
}
