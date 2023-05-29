package ru.yandex.practicum.filmorate.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

public class Validator {
    public static void validate(Film film) throws ValidationException {
        if (film.getReleaseDate().isBefore(LocalDate.of(1885, 12, 28))) {
            throw new ValidationException(HttpStatus.BAD_REQUEST, "Кино еще не родилось!");
        }
    }

    public static void validate(User user) throws ValidationException {
        if (StringUtils.isBlank(user.getLogin()) || user.getLogin().contains(" ")) {
            throw new ValidationException(HttpStatus.BAD_REQUEST,
                    "Логин не должен содержать пробелы и не может быть пустым");
        }
        if (StringUtils.isBlank(user.getEmail()) || !user.getEmail().contains("@")) {
            throw new ValidationException(HttpStatus.BAD_REQUEST,
                    "Электронная почта не может быть пустой и должна содержать символ @");
        }
        if ((user.getName() == null) || (user.getName().isBlank())) {
            user.setName(user.getLogin());
        }
    }
}
