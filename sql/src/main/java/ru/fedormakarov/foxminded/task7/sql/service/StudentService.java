package ru.fedormakarov.foxminded.task7.sql.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import ru.fedormakarov.foxminded.task7.sql.businesslogic.Util;
import ru.fedormakarov.foxminded.task7.sql.dao.StudentDAO;
import ru.fedormakarov.foxminded.task7.sql.entity.Student;

public class StudentService extends Util implements StudentDAO {

    @Override
    public Student getById(int studentId) throws SQLException {
        String sql = "SELECT student_id, first_name, last_name, group_id FROM students WHERE student_id=?";
        Student student = new Student();
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setInt(1, studentId);
            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                if (resultSet.next()) {
                    student.setStudentId(resultSet.getInt("student_id"));
                    student.setFirstName(resultSet.getString("first_name"));
                    student.setLastName(resultSet.getString("last_name"));
                    student.setGroupId(resultSet.getInt("group_id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return student;
    }

    @Override
    public boolean add(Student student) throws SQLException {
        String sql = "INSERT INTO students (student_id, first_name, last_name, group_id) VALUES (?,?,?,?)";
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setInt(1, student.getStudentId());
            preparedStatement.setString(2, student.getFirstName());
            preparedStatement.setString(3, student.getLastName());
            preparedStatement.setInt(4, student.getGroupId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int studentId) throws SQLException {
        String sql = "DELETE FROM students WHERE student_id=?";
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setInt(1, studentId);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Student student) throws SQLException {
        String sql = "UPDATE students SET first_name=?, last_name=?, group_id=? WHERE student_id=?";
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setString(1, student.getFirstName());
            preparedStatement.setString(2, student.getLastName());
            preparedStatement.setInt(3, student.getGroupId());
            preparedStatement.setInt(4, student.getStudentId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Student> getAll() throws SQLException {
        List<Student> studentList = new ArrayList<>();
        String sql = "SELECT student_id, first_name, last_name, group_id FROM students";
        try (Connection connection = getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);) {
            while (resultSet.next()) {
                Student student = new Student();
                student.setStudentId(resultSet.getInt("student_id"));
                student.setFirstName(resultSet.getString("first_name"));
                student.setLastName(resultSet.getString("last_name"));
                student.setGroupId(resultSet.getInt("group_id"));
                studentList.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return studentList;
    }

    public List<Student> getAllStudentsFromCourse(String courseName) throws SQLException {
        List<Student> studentList = new ArrayList<>();
        String sql = "SELECT st.student_id, first_name, last_name, group_id FROM students st "
                + "JOIN students_courses stcu ON(st.student_id = stcu.student_id) "
                + "WHERE course_id = (SELECT course_id FROM courses WHERE course_name=?) ORDER BY st.student_id";
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setString(1, courseName);
            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                while (resultSet.next()) {
                    Student student = new Student();
                    student.setStudentId(resultSet.getInt("student_id"));
                    student.setFirstName(resultSet.getString("first_name"));
                    student.setLastName(resultSet.getString("last_name"));
                    student.setGroupId(resultSet.getInt("group_id"));
                    studentList.add(student);
                }
            }
            return studentList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
