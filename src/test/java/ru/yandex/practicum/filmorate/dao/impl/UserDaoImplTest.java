package ru.yandex.practicum.filmorate.dao.impl;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.dao.UserDao;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UserDaoImplTest {

    private final UserDao userDao;
    private final FriendsDaoImpl friendsDao;

    @Test
    void add() {
        User newUser = new User("qwerty@mail.ru", "Whats", LocalDate.of(1990, 12, 23));
        User addedUser = userDao.add(newUser);
        assertEquals(6, addedUser.getId());
    }

    @Test
    void update() {
        User newUser = new User("asdfg@mail.ru", "Uuuup", LocalDate.of(1995, 10, 25));
        newUser.setId(1);
        userDao.update(newUser);
        assertEquals(newUser, userDao.getById(1));
    }

    @Test
    void updateWithWrongId() {
        User newUser = new User("zxcvb@mail.ru", "Amigo", LocalDate.of(1995, 10, 25));
        newUser.setId(55);
        NotFoundException e = assertThrows(NotFoundException.class, () -> userDao.update(newUser));
        assertEquals("Пользователь по ID 55 не найден!", e.getMessage());
    }

    @Test
    void delete() {
        int result = userDao.delete(5);
        assertEquals(1, result);
    }

    @Test
    void deleteWithWrongId() {
        int result = userDao.delete(12);
        assertEquals(0, result);
    }

    @Test
    void getById() {
        User user = userDao.getById(2);
        assertEquals("Orange", user.getName());
    }

    @Test
    void getByWrongId() {
        NotFoundException e = assertThrows(NotFoundException.class, () -> userDao.getById(48));
        assertEquals("Пользователь по ID 48 не найден!", e.getMessage());
    }

    @Test
    void getAll() {
        List<User> users = userDao.getAll();
        assertEquals(4, users.size());
    }

    @Test
    void addFriend() {
        int result = friendsDao.add(1, 2);
        assertEquals(1, result);
    }

    @Test
    void addFriendByWrongId() {
        NotFoundException e1 = assertThrows(NotFoundException.class, () -> friendsDao.add(-1, 2));
        NotFoundException e2 = assertThrows(NotFoundException.class, () -> friendsDao.add(1, -2));
        assertEquals("передан неверный идентификатор!", e1.getMessage());
        assertEquals("передан неверный идентификатор!", e2.getMessage());
    }

    @Test
    void removeFriend() {
        int result = friendsDao.remove(2, 4);
        assertEquals(1, result);
    }

    @Test
    void removeFriendWithWrongId() {
        int result1 = friendsDao.remove(-1, 2);
        int result2 = friendsDao.remove(1, -2);
        assertEquals(0, result1);
        assertEquals(0, result2);
    }

    @Test
    void getFriends() {
        List<User> friends = userDao.getFriends(4);
        assertEquals(2, friends.size());
    }

    @Test
    void getFriendsWithWrongId() {
        List<User> friends = userDao.getFriends(55);
        assertEquals(0, friends.size());
    }

    @Test
    void getMutualFriends() {
        List<User> friends = userDao.getMutualFriends(2, 4);
        assertEquals(1, friends.size());
    }

    @Test
    void getMutualFriendsWithWrongId() {
        List<User> friends = userDao.getMutualFriends(-2, 4);
        assertEquals(0, friends.size());
    }
}