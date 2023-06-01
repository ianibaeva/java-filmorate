package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class User {

    private int id;
    @Email
    private final String email;
    @NotBlank
    private final String login;
    private String name;
    @Past
    private final LocalDate birthday;
    private Set<Integer> friendsId = new HashSet<>();

}
