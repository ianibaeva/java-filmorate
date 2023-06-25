package ru.yandex.practicum.filmorate.dao.impl;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.FilmDao;
import ru.yandex.practicum.filmorate.dao.SimpleDao;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.*;

@Component
public class FilmDaoImpl implements FilmDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleDao<Mpa> mpaDao;

    public FilmDaoImpl(JdbcTemplate jdbcTemplate, SimpleDao<Mpa> mpaDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.mpaDao = mpaDao;
    }

    @Override
    public Film add(Film film) {
        String sqlQuery = "INSERT INTO films (name, description, release_date, duration, rate, mpa) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(sqlQuery, new String[]{"film_id"});
            statement.setString(1, film.getName());
            statement.setString(2, film.getDescription());
            statement.setDate(3, Date.valueOf(film.getReleaseDate()));
            statement.setInt(4, film.getDuration());
            statement.setInt(5, film.getRate());
            statement.setInt(6, film.getMpa().getId());
            return statement;
        }, keyHolder);
        film.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());

        return film;
    }

    @Override
    public Film update(Film film) {
        String sqlQuery = "UPDATE films SET name = ?, description = ?, " +
                "release_date = ?, duration = ?, rate = ?,  mpa = ? " +
                "WHERE film_id = ?";
        int result = jdbcTemplate.update(sqlQuery, film.getName(), film.getDescription(),
                film.getReleaseDate(), film.getDuration(), film.getRate(),
                film.getMpa().getId(), film.getId());
        if (result != 1) {
            throw new NotFoundException("Фильм по ID " + film.getId() + " не найден!");
        }

        return film;
    }

    @Override
    public int delete(int id) {
        String sqlQuery = "DELETE FROM films WHERE film_id = ?";

        return jdbcTemplate.update(sqlQuery, id);
    }

    @Override
    public Film getById(int id) {
        String sqlQuery = "SELECT f.film_id, f.name, f.description, f.release_date, f.duration, f.rate, m.mpa_id, m.description as mdesc, g.genre_id, g.description as genre " +
                "FROM films AS f " +
                "LEFT JOIN MPA_RATING AS m ON f.mpa = m.mpa_id " +
                "LEFT JOIN FILM_GENRE AS fg ON f.film_id = fg.film_id " +
                "LEFT JOIN GENRES AS g ON fg.genre_id = g.genre_id where f.film_id = ?";

        try {
            return jdbcTemplate.query(sqlQuery, arrayListResultSetExtractor, id).get(0);
        } catch (DataAccessException | IndexOutOfBoundsException e) {
            throw new NotFoundException("Фильм по ID " + id + " не найден!");
        }
    }

    @Override
    public List<Film> getAll() {

        String sqlQuery = "SELECT f.film_id, f.name, f.description, f.release_date, f.duration, f.rate, m.mpa_id, m.description as mdesc, g.genre_id, g.description as genre " +
                "FROM films AS f " +
                "LEFT JOIN MPA_RATING AS m ON f.mpa = m.mpa_id " +
                "LEFT JOIN FILM_GENRE AS fg ON f.film_id = fg.film_id " +
                "LEFT JOIN GENRES AS g ON fg.genre_id = g.genre_id ";


        return jdbcTemplate.query(sqlQuery, arrayListResultSetExtractor);
    }

    @Override
    public List<Film> getMostPopular(int count) {
        String sqlQuery = "SELECT f.film_id, f.name, f.description, f.release_date, f.duration, f.rate, m.mpa_id, m.description as mdesc, g.genre_id, g.description as genre " +
                "FROM films AS f " +
                "LEFT JOIN MPA_RATING AS m ON f.mpa = m.mpa_id " +
                "LEFT JOIN FILM_GENRE AS fg ON f.film_id = fg.film_id " +
                "LEFT JOIN GENRES AS g ON fg.genre_id = g.genre_id ORDER BY rate desc Limit ?";

        ArrayList<Film> result = jdbcTemplate.query(sqlQuery, arrayListResultSetExtractor, count);
        result.sort(Comparator.comparingInt(Film::getRate).reversed());
        return result;
    }


    ResultSetExtractor<ArrayList<Film>> arrayListResultSetExtractor = rs -> {
        Map<Integer, Film> map = new HashMap();

        while (rs.next()) {
            int filmId = rs.getInt("film_id");
            Film film = map.get(filmId);
            if (film == null) {
                film = new Film();
                film.setId(filmId);
                film.setName(rs.getString("name"));
                film.setDescription(rs.getString("description"));
                film.setReleaseDate(rs.getDate("release_date").toLocalDate());
                film.setDuration(rs.getInt("duration"));
                film.setRate(rs.getInt("rate"));
                Mpa mpa = new Mpa();
                mpa.setId(rs.getInt("mpa_id"));
                mpa.setName(rs.getString("mdesc"));
                film.setMpa(mpa);
                map.put(filmId, film);
            }
            Genre genre = new Genre();
            genre.setName(rs.getString("genre"));
            genre.setId(rs.getInt("genre_id"));
            film.getGenres().add(genre);
        }

        return new ArrayList<>(map.values());
    };
}
