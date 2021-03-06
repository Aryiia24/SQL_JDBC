package ru.fedormakarov.foxminded.task7.sql.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ru.fedormakarov.foxminded.task7.sql.businesslogic.DatabaseConnector;
import ru.fedormakarov.foxminded.task7.sql.dao.StudentCourseDAO;
import ru.fedormakarov.foxminded.task7.sql.entity.StudentCourse;

public class StudentCourseService implements StudentCourseDAO {

    private Connection connection = DatabaseConnector.getInstance().getConnection();

    @Override
    public boolean save(StudentCourse studentCourse) {

        String sql = "INSERT INTO students_courses (student_id, course_id) VALUES (?,?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setInt(1, studentCourse.getStudentId());
            preparedStatement.setInt(2, studentCourse.getCourseId());
            if (preparedStatement.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't add studentCourse into table", e);
        }
        return false;
    }

    @Override
    public boolean update(StudentCourse studentCourse) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<StudentCourse> getAll() {
        List<StudentCourse> studentCourseList = new ArrayList<>();
        String sql = "SELECT student_id, course_id FROM students_courses";

        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql);) {
            while (resultSet.next()) {
                StudentCourse studentCourse = new StudentCourse();
                studentCourse.setStudentId(resultSet.getInt("student_id"));
                studentCourse.setStudentId(resultSet.getInt("course_id"));
                studentCourseList.add(studentCourse);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't get all studentCourses", e);
        }
        return studentCourseList;
    }

    @Override
    public boolean delete(int id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public StudentCourse getById(int id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<StudentCourse> getListStudentCoursesByStudentId(int studentId) {
        List<StudentCourse> studentCourseList = new ArrayList<>();

        String sql = "SELECT student_id, course_id FROM students_courses WHERE student_id=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setInt(1, studentId);
            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                while (resultSet.next()) {
                    StudentCourse studentCourse = new StudentCourse();
                    studentCourse.setStudentId(resultSet.getInt("student_id"));
                    studentCourse.setCourseId(resultSet.getInt("course_id"));
                    studentCourseList.add(studentCourse);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't get studentCourses by student_id", e);
        }
        return studentCourseList;
    }

    public boolean removeStudentCourse(int studentId, int courseId) {
        String sql = "DELETE FROM students_courses WHERE student_id=? AND course_id=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setInt(1, studentId);
            preparedStatement.setInt(2, courseId);

            if (preparedStatement.executeUpdate() > 0) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't remove studentCourse", e);
        }
        return false;
    }
}