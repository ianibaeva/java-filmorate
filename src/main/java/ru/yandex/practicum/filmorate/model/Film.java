package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import ru.yandex.practicum.filmorate.utils.ReleaseDateValidation;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class Film {
    @Getter
    @Setter
    private int id;
    @NotBlank
    @NonNull
    private final String name;
    @Size(min = 1, max = 200)
    private final String description;
    @ReleaseDateValidation(message = "Неверная дата создания фильма", dateStart = "1895.12.28")
    private final LocalDate releaseDate;
    @Positive
    private final int duration;
    @Setter
    @Getter
    private Set<Integer> usersLiked = new HashSet<>();
    @Setter
    @Getter
    private int likes = 0;

}


