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
    public void add(StudentCourse studentCourse) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "INSERT INTO students_courses (student_id, course_id) VALUES (?,?)";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, studentCourse.getStudentId());
            preparedStatement.setInt(2, studentCourse.getCourseId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    @Override
    public void update(StudentCourse studentCourse) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection connection = getConnection();
        String sql = "UPDATE students_courses SET student_id=?, course_id=?";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, studentCourse.getStudentId());
            preparedStatement.setInt(2, studentCourse.getCourseId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    @Override
    public List<StudentCourse> getAll() throws SQLException {
        List<StudentCourse> studentCourseList = new ArrayList<>();
        Statement statement = null;
        Connection connection = getConnection();
        String sql = "SELECT student_id, course_id FROM students_courses";

        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                StudentCourse studentCourse = new StudentCourse();
                studentCourse.setStudentId(resultSet.getInt("student_id"));
                studentCourse.setStudentId(resultSet.getInt("course_id"));
                studentCourseList.add(studentCourse);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return studentCourseList;
    }

    @Override
    public void delete(int id) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public StudentCourse getById(int id) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<StudentCourse> getListStudentCoursesByStudentId(int student_id) throws SQLException {
        List<StudentCourse> studentCourseList = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        Connection connection = getConnection();
        String sql = "SELECT student_id, course_id FROM students_courses WHERE student_id=?";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, student_id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                StudentCourse studentCourse = new StudentCourse();
                studentCourse.setStudentId(resultSet.getInt("student_id"));
                studentCourse.setCourseId(resultSet.getInt("course_id"));

                studentCourseList.add(studentCourse);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return studentCourseList;
    }

    public void removeStudentCourse(int student_id, int course_id) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection connection = getConnection();

        String sql = "DELETE FROM students_courses WHERE student_id=? AND course_id=?";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, student_id);
            preparedStatement.setInt(2, course_id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }
}