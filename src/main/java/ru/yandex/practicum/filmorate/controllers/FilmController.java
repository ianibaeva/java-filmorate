package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(value = "/films")
public class FilmController {

    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService service) {
        this.filmService = service;
    }

    @GetMapping
    public List<Film> getFilms() {
        log.info("Получен GET запрос /films.");
        return filmService.getAll();
    }

    @GetMapping(value = "/{id}")
    public Film getById(@PathVariable int id) {
        log.info("Получен GET запрос /films/{}.", id);
        return filmService.getById(id);
    }

    @PostMapping
    public Film add(@RequestBody @Valid Film film) {
        log.info("Получен POST запрос /films. Передано: {}", film);
        return filmService.add(film);
    }

    @PutMapping
    public Film update(@RequestBody @Valid Film film) {
        log.info("Получен PUT запрос /films. Передано: {}", film);
        return filmService.update(film);
    }

    @PutMapping(value = "/{id}/like/{userId}")
    public void addLike(@PathVariable int id, @PathVariable int userId) {
        log.info("Получен PUT запрос /films/{}/like/{}.", id, userId);
        filmService.addLike(id, userId);
    }

    @DeleteMapping(value = "/{id}/like/{userId}")
    public void removeLike(@PathVariable int id, @PathVariable int userId) {
        log.info("Получен DELETE запрос /films/{}/like/{}.", id, userId);
        filmService.removeLike(id, userId);
    }

    @GetMapping(value = "/popular")
    public List<Film> getMostPopular(@RequestParam(defaultValue = "10") int count) {
        log.info("Получен GET запрос /films/popular?count={}.", count);
        return filmService.getMostPopular(count);
    }
}
