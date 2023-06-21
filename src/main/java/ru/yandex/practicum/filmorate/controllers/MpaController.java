package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(value = "/mpa")
public class MpaController {

    private final MpaService service;

    @Autowired
    public MpaController(MpaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Mpa> getAll() {
        log.info("Получен GET запрос /mpa.");
        return service.getAll();
    }

    @GetMapping(value = "/{id}")
    public Mpa getById(@PathVariable int id) {
        log.info("Получен GET запрос /mpa/{}.", id);
        return service.getById(id);
    }
}
