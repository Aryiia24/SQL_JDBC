package ru.fedormakarov.foxminded.task7.sql.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ru.fedormakarov.foxminded.task7.sql.businesslogic.Util;
import ru.fedormakarov.foxminded.task7.sql.dao.StudentCourseDAO;
import ru.fedormakarov.foxminded.task7.sql.entity.StudentCourse;

public class StudentCourseService extends Util implements StudentCourseDAO {

    @Override
    public boolean add(StudentCourse studentCourse) throws SQLException {

        String sql = "INSERT INTO students_courses (student_id, course_id) VALUES (?,?)";

        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setInt(1, studentCourse.getStudentId());
            preparedStatement.setInt(2, studentCourse.getCourseId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(StudentCourse studentCourse) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<StudentCourse> getAll() throws SQLException {
        List<StudentCourse> studentCourseList = new ArrayList<>();
        String sql = "SELECT student_id, course_id FROM students_courses";

        try (Connection connection = getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);) {
            while (resultSet.next()) {
                StudentCourse studentCourse = new StudentCourse();
                studentCourse.setStudentId(resultSet.getInt("student_id"));
                studentCourse.setStudentId(resultSet.getInt("course_id"));
                studentCourseList.add(studentCourse);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return studentCourseList;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public StudentCourse getById(int id) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<StudentCourse> getListStudentCoursesByStudentId(int studentId) throws SQLException {
        List<StudentCourse> studentCourseList = new ArrayList<>();

        String sql = "SELECT student_id, course_id FROM students_courses WHERE student_id=?";

        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
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
        }
        return studentCourseList;
    }

    public boolean removeStudentCourse(int studentId, int courseId) throws SQLException {
        String sql = "DELETE FROM students_courses WHERE student_id=? AND course_id=?";

        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setInt(1, studentId);
            preparedStatement.setInt(2, courseId);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}