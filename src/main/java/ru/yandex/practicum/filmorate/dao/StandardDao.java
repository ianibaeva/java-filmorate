package ru.yandex.practicum.filmorate.dao;

public interface StandardDao<T> extends SimpleDao<T> {

    T add(T t);

    T update(T t);

    int delete(int id);
}
