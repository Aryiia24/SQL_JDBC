package ru.fedormakarov.foxminded.task7.sql.dao;

import java.sql.SQLException;
import java.util.List;

public interface GenericDAO<T> {

    T getById(int id) throws SQLException;

    boolean save(T entity) throws SQLException;

    boolean delete(int id) throws SQLException;

    boolean update(T t) throws SQLException;

    List<T> getAll() throws SQLException;
}
