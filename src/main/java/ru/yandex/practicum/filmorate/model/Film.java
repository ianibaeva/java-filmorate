package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.utils.ReleaseDateValidation;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.*;

@Data
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class Film {
    private int id;
    @NotBlank
    @NonNull
    private String name;
    @Size(min = 1, max = 200)
    private String description;
    @ReleaseDateValidation(message = "Неверная дата создания фильма", dateStart = "1895.12.28")
    private LocalDate releaseDate;
    @Positive
    private int duration;
    private Set<Integer> usersLiked = new HashSet<>();
    private Mpa mpa;
    private int rate;
    private List<Genre> genres = new ArrayList<>();

    public Film(String name, String description, LocalDate releaseDate, int duration, Mpa mpa) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.mpa = mpa;
    }
}



