package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmDao extends StandardDao<Film> {

    List<Film> getMostPopular(int count);
}
