package ru.fedormakarov.foxminded.task7.sql.dao;

import java.sql.SQLException;
import java.util.List;

import ru.fedormakarov.foxminded.task7.sql.entity.StudentCourse;

public interface StudentCourseDAO extends GenericDAO<StudentCourse> {

    List<StudentCourse> getListStudentCoursesByStudentId(int student_id) throws SQLException;

}
