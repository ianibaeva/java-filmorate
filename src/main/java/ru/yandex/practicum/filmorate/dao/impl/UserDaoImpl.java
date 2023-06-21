package ru.yandex.practicum.filmorate.dao.impl;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.UserDao;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Component
public class UserDaoImpl implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User add(User user) {
        String sqlQuery = "INSERT INTO users (e_mail, login, name, birthday) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(sqlQuery, new String[]{"user_id"});
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getLogin());
            statement.setString(3, user.getName());
            statement.setDate(4, Date.valueOf(user.getBirthday()));
            return statement;
        }, keyHolder);
        user.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());

        return user;
    }

    @Override
    public User update(User user) {
        String sqlQuery = "UPDATE users SET e_mail = ?, login = ?, name = ?, birthday = ? WHERE user_id = ?";
        int result = jdbcTemplate.update(sqlQuery, user.getEmail(), user.getLogin(),
                user.getName(), user.getBirthday(), user.getId());
        if (result != 1) {
            throw new NotFoundException("Пользователь по ID " + user.getId() + " не найден!");
        }

        return user;
    }

    @Override
    public int delete(int id) {
        String sqlQuery = "DELETE FROM users WHERE user_id = ?";

        return jdbcTemplate.update(sqlQuery, id);
    }

    @Override
    public User getById(int id) {
        String sqlQuery = "SELECT user_id, e_mail, login, name, birthday FROM users WHERE user_id = ?";
        try {
            return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToUser, id);
        } catch (DataAccessException e) {
            throw new NotFoundException("Пользователь по ID " + id + " не найден!");
        }
    }

    @Override
    public List<User> getAll() {
        String sqlQuery = "SELECT user_id, e_mail, login, name, birthday FROM users";

        return jdbcTemplate.query(sqlQuery, this::mapRowToUser);
    }

    @Override
    public List<User> getFriends(int userId) {
        String sqlQuery = "SELECT user_id, e_mail, login, name, birthday FROM users " +
                "WHERE user_id IN (SELECT friend_id FROM friends WHERE user_id = ?)";

        return jdbcTemplate.query(sqlQuery, this::mapRowToUser, userId);
    }

    @Override
    public List<User> getMutualFriends(int userId, int friendId) {
        String sqlQuery = "SELECT user_id, e_mail, login, name, birthday FROM users " +
                "WHERE user_id IN " +
                "(SELECT friend_id FROM friends WHERE user_id = ? AND friend_id NOT IN (?, ?) AND " +
                "friend_id IN (SELECT friend_id FROM friends WHERE user_id = ?))";

        return jdbcTemplate.query(sqlQuery, this::mapRowToUser, userId, friendId, userId, friendId);
    }

    private User mapRowToUser(ResultSet resultSet, int rowNum) throws SQLException {
        User user = new User(resultSet.getString("e_mail"),
                resultSet.getString("login"), resultSet.getDate("birthday").toLocalDate());
        user.setName(resultSet.getString("name"));
        user.setId(resultSet.getInt("user_id"));

        return user;
    }
}
