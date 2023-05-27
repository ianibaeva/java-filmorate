package ru.yandex.practicum.filmorate.utils;

import java.util.HashMap;

public enum GenerateID {

    INSTANCE;

    private final HashMap<Class<?>, Integer> identifier = new HashMap<>();

    public int generateId(Class<?> clazz) {
        if (identifier.containsKey(clazz)) {
            int id = identifier.get(clazz);
            identifier.put(clazz, ++id);
            return id;

        } else {
            identifier.put(clazz, 1);
            return 1;
        }
    }

}
