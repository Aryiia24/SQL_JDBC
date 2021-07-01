package ru.fedormakarov.foxminded.task7.sql.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ru.fedormakarov.foxminded.task7.sql.businesslogic.DatabaseConnector;
import ru.fedormakarov.foxminded.task7.sql.dao.CourseDAO;
import ru.fedormakarov.foxminded.task7.sql.entity.Course;
import ru.fedormakarov.foxminded.task7.sql.entity.StudentCourse;

public class CourseService implements CourseDAO {

    private Connection connection = DatabaseConnector.getInstance().getConnection();

    @Override
    public boolean save(Course course) {
        String sql = "INSERT INTO courses (id, course_name, course_description) VALUES(?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setInt(1, course.getCourseId());
            preparedStatement.setString(2, course.getCourseName());
            preparedStatement.setString(3, course.getCourseDescription());
            if (preparedStatement.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't add Course", e);
        }
        return false;
    }

    @Override
    public boolean update(Course course) {
        String sql = "UPDATE courses SET course_name=?, course_description=? WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setString(1, course.getCourseName());
            preparedStatement.setString(2, course.getCourseDescription());
            preparedStatement.setInt(3, course.getCourseId());
            if (preparedStatement.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't update Course", e);

        }
        return false;
    }

    @Override
    public List<Course> getAll() {
        List<Course> courseList = new ArrayList<>();
        String sql = "SELECT id, course_name, course_description FROM courses";
        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql);) {
            while (resultSet.next()) {
                Course course = constructCourseFromTable(resultSet);
                courseList.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't get all courses", e);
        }
        return courseList;
    }

    @Override
    public Course getById(int courseId) {
        String sql = "SELECT id, course_name, course_description FROM courses WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setInt(1, courseId);
            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                if (resultSet.next()) {
                    return constructCourseFromTable(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't get course", e);
        }
        return null;
    }

    @Override
    public boolean delete(int courseId) {
        String sql = "DELETE FROM courses WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setInt(1, courseId);
            if (preparedStatement.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't delete course", e);

        }
        return false;
    }

    public List<Course> getListCoursesByStudentCourses(List<StudentCourse> studentCoursesList) {
        List<Course> courseList = new ArrayList<>();
        for (StudentCourse studentCourse : studentCoursesList) {
            Course course = getById(studentCourse.getCourseId());
            courseList.add(course);
        }
        return courseList;
    }

    private Course constructCourseFromTable(ResultSet resultSet) throws SQLException {
        Course course = new Course();
        course.setCourseId(resultSet.getInt("id"));
        course.setCourseName(resultSet.getString("course_name"));
        course.setCourseDescription(resultSet.getString("course_description"));
        return course;
    }
}
