package ru.fedormakarov.foxminded.task7.sql.dao;

import java.util.List;

import ru.fedormakarov.foxminded.task7.sql.entity.Student;

public interface StudentDAO extends GenericDAO<Student> {
    List<Student> getAllStudentsFromCourse(String courseName);
}
