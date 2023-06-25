package ru.yandex.practicum.filmorate.utils;

import java.util.HashMap;

public enum IdGenerator {

    INSTANCE;

    private final HashMap<Class<?>, Integer> identifier = new HashMap<>();

    public int generateId(Class<?> clazz) {
        int id;
        if (identifier.containsKey(clazz)) {
            id = identifier.get(clazz);
            identifier.put(clazz, ++id);

        } else {
            id = 1;
            identifier.put(clazz, id);
        }
        return id;
    }

}
