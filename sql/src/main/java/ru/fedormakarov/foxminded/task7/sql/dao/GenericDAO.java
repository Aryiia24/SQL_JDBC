package ru.fedormakarov.foxminded.task7.sql.dao;

import java.sql.SQLException;
import java.util.List;

public interface GenericDAO<T> {

    T getById(int id) throws SQLException;

    void add(T entity) throws SQLException;

    void delete(int id) throws SQLException;

    void update(T t) throws SQLException;

    List<T> getAll() throws SQLException;
}
