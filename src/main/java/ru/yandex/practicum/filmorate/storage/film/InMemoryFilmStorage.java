package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.utils.IdGenerator;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static ru.yandex.practicum.filmorate.validator.Validator.validate;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final HashMap<Integer, Film> films = new HashMap<>();

    @Override
    public Film add(Film film) {
        validate(film);
        film.setId(IdGenerator.INSTANCE.generateId(Film.class));
        films.put(film.getId(), film);
        log.info("Фильм добавлен");
        return film;
    }

    @Override
    public Film update(Film film) {
        validate(film);
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            log.info("Фильм обновлен.");
        } else {
            log.debug("Фильм по ID " + film.getId() + " не найден.");
            throw new NotFoundException("Фильм по ID " + film.getId() + " не найден.");
        }
        return film;
    }

    @Override
    public Film delete(Film film) {
        return films.remove(film.getId());
    }

    @Override
    public Film getById(int id) {
        if (films.containsKey(id)) {
            log.info("Вывод фильм по ID " + id);
            return films.get(id);
        } else {
            log.debug("Фильм по ID " + id + " не найден.");
            throw new NotFoundException("Фильм по ID " + id + " не найден.");
        }
    }

    @Override
    public List<Film> getAll() {
        log.debug("Текущее количество фильмов: {}", films.size());
        return new ArrayList<>(films.values());
    }

    @Override
    public List<Film> getMostPopular(int count) {
        log.info("Список наиболее популярных фильмов.");
        return films.values()
                .stream().
                sorted(this::compare)
                .limit(count)
                .collect(Collectors.toList());
    }

    private int compare(Film f0, Film f1) {
        return f1.getLikes() - f0.getLikes();
    }
}
