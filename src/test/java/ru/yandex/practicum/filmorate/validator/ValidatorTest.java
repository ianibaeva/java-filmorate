package ru.yandex.practicum.filmorate.validator;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static ru.yandex.practicum.filmorate.validator.Validator.validate;

class ValidatorTest {

    @Test
    public void shouldNotValidateOldFilm() {
        Film oldFilm = new Film("name", "description", LocalDate.of(1600, 11, 11), 1);
        ValidationException e = assertThrows(ValidationException.class, () -> validate(oldFilm));
        assertEquals("Кино еще не родилось!", e.getMessage());
    }

    @Test
    public void shouldValidateFirstFilm() {
        Film firstFilm = new Film("Name", "Description", LocalDate.of(1895, 12, 28), 1);
        assertDoesNotThrow(() -> validate(firstFilm));
    }

    @Test
    public void shouldNotValidateLoginWithSpaces() {
        User userWithSpaceInLogin = new User("user@yandex.ru", "user user",
                LocalDate.of(1989, 6, 12));
        userWithSpaceInLogin.setName("name");
        ValidationException e = assertThrows(ValidationException.class, () -> validate(userWithSpaceInLogin));
        assertEquals("Логин не должен содержать пробелы и не может быть пустым", e.getMessage());
    }

    @Test
    public void shouldUseLoginInsteadOfBlankName() throws ValidationException {
        User user = new User("user@yandex.ru", "user", LocalDate.of(1989, 6, 12));
        user.setName("");
        validate(user);
        assertEquals(user.getLogin(), user.getName());
    }

    @Test
    public void shouldUseLoginInsteadOfNullName() throws ValidationException {
        User user = new User("user@yandex.ru", "user", LocalDate.of(1989, 6, 12));
        validate(user);
        assertEquals(user.getLogin(), user.getName());
    }
}