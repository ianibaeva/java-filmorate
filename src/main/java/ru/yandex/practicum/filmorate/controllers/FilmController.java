package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.utils.GenerateID;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static ru.yandex.practicum.filmorate.validator.Validator.validate;

@Slf4j
@RestController
@RequestMapping(value = "/films")
public class FilmController {

    private final HashMap<Integer, Film> films = new HashMap<>();

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) throws ValidationException {
        validate(film);
        film.setId(GenerateID.INSTANCE.generateId(Film.class));
        films.put(film.getId(), film);
        log.info("Фильм добавлен");
        return film;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) throws ValidationException {
        validate(film);
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            log.info("Фильм обновлен.");
        } else {
            log.debug("Фильм не найден.");
            throw new NotFoundException("Film not found.");
        }
        return film;
    }

    @GetMapping
    public List<Film> getFilms() {
        log.debug("Текущее количество фильмов: {}", films.size());
        return new ArrayList<>(films.values());
    }
}
