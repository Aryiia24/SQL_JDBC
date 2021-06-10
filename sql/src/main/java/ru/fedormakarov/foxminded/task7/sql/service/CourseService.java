package ru.fedormakarov.foxminded.task7.sql.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ru.fedormakarov.foxminded.task7.sql.businesslogic.Util;
import ru.fedormakarov.foxminded.task7.sql.dao.CourseDAO;
import ru.fedormakarov.foxminded.task7.sql.entity.Course;
import ru.fedormakarov.foxminded.task7.sql.entity.StudentCourse;

public class CourseService extends Util implements CourseDAO {

    @Override
    public void add(Course course) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "INSERT INTO courses (course_id, course_name, course_description) VALUES(?,?,?)";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, course.getCourseId());
            preparedStatement.setString(2, course.getCourseName());
            preparedStatement.setString(3, course.getCourseDescription());
            preparedStatement.executeUpdate();
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
    }

    @Override
    public void update(Course course) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Course> getAll() throws SQLException {
        List<Course> courseList = new ArrayList<>();
        Connection connection = getConnection();
        Statement statement = null;
        String sql = "SELECT course_id, course_name, course_description FROM courses";

        try {
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Course course = new Course();
                course.setCourseId(resultSet.getInt("course_id"));
                course.setCourseName(resultSet.getString("course_name"));
                course.setCourseDescription(resultSet.getString("course_description"));
                courseList.add(course);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return courseList;
    }

    @Override
    public Course getById(int id) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(int id) throws SQLException {
        throw new UnsupportedOperationException();
    }

    public List<Course> getListCoursesByStudentCourses(List<StudentCourse> studentCoursesList) throws SQLException {
        List<Course> courseList = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "SELECT course_id, course_name, course_description FROM courses WHERE course_id = ?";

        try {
            preparedStatement = connection.prepareStatement(sql);
            for (StudentCourse studentCourse : studentCoursesList) {
                preparedStatement.setInt(1, studentCourse.getCourseId());
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Course course = new Course();
                    course.setCourseId(resultSet.getInt("course_id"));
                    course.setCourseName(resultSet.getString("course_name"));
                    course.setCourseDescription(resultSet.getString("course_description"));
                    courseList.add(course);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return courseList;
    }

}
