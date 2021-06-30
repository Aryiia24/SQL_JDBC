package ru.fedormakarov.foxminded.task7.sql.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import ru.fedormakarov.foxminded.task7.sql.businesslogic.DatabaseConnector;
import ru.fedormakarov.foxminded.task7.sql.dao.StudentDAO;
import ru.fedormakarov.foxminded.task7.sql.entity.Student;

public class StudentService implements StudentDAO {

    private Connection connection = DatabaseConnector.getInstance().getConnection();

    @Override
    public Student getById(int studentId) {
        String sql = "SELECT id, first_name, last_name, group_id FROM students WHERE id=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setInt(1, studentId);
            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                if (resultSet.next()) {
                    Student student = constructStudentFromTable(resultSet);
                    return student;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Cannot get Student", e);
        }
        return null;

    }

    @Override
    public boolean save(Student student) {
        String sql = "INSERT INTO students (id, first_name, last_name, group_id) VALUES (?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setInt(1, student.getStudentId());
            preparedStatement.setString(2, student.getFirstName());
            preparedStatement.setString(3, student.getLastName());
            preparedStatement.setInt(4, student.getGroupId());
            if (preparedStatement.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Cannot add Student", e);
        }
        return false;
    }

    @Override
    public boolean delete(int studentId) {
        String sql = "DELETE FROM students WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setInt(1, studentId);

            if (preparedStatement.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Cannot delete Student", e);
        }
        return false;
    }

    @Override
    public boolean update(Student student) {
        String sql = "UPDATE students SET first_name=?, last_name=?, group_id=? WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setString(1, student.getFirstName());
            preparedStatement.setString(2, student.getLastName());
            preparedStatement.setInt(3, student.getGroupId());
            preparedStatement.setInt(4, student.getStudentId());
            if (preparedStatement.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Cannot update Student", e);
        }
        return false;
    }

    @Override
    public List<Student> getAll() {
        List<Student> studentList = new ArrayList<>();
        String sql = "SELECT id, first_name, last_name, group_id FROM students";
        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql);) {
            while (resultSet.next()) {
                studentList.add(constructStudentFromTable(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't get all students", e);
        }
        return studentList;
    }

    public List<Student> getAllStudentsFromCourse(String courseName) {
        List<Student> studentList = new ArrayList<>();
        String sql = "SELECT st.id, first_name, last_name, group_id FROM students st "
                + "JOIN students_courses stcu ON(st.id = stcu.student_id) "
                + "WHERE course_id = (SELECT id FROM courses WHERE course_name=?) ORDER BY st.id";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setString(1, courseName);
            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                while (resultSet.next()) {
                    studentList.add(constructStudentFromTable(resultSet));
                }
            }
            return studentList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't get all students from course", e);
        }
    }

    private static Student constructStudentFromTable(ResultSet resultSet) {
        Student student = new Student();
        try {
            student.setStudentId(resultSet.getInt("id"));
            student.setFirstName(resultSet.getString("first_name"));
            student.setLastName(resultSet.getString("last_name"));
            student.setGroupId(resultSet.getInt("group_id"));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't construct student", e);
        }
        return student;
    }
}
