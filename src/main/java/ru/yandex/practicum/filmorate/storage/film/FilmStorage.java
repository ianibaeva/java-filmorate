package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;
import java.util.List;

public interface FilmStorage {
    Film add(Film film);
    Film update(Film film);
    Film delete(Film film);
    Film getById(int id);
    List<Film> getAll();
    List<Film> getMostPopular(int count);
}
