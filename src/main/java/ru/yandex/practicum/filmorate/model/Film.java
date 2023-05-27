package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;
import lombok.Setter;
import ru.yandex.practicum.filmorate.utils.ReleaseDateValidation;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@Setter
public class Film {
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

}


