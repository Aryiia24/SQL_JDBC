package ru.fedormakarov.foxminded.task7.sql.businesslogic;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.Test;

class TableCreatorTest {
    TableCreator tableCreator = new TableCreator();
    Util util = new Util();

    @Test
    void testOnCreateStudentTable() throws SQLException {
        String sql = "SELECT student_id, first_name, last_name, group_id FROM students";
        try (Connection connection = util.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);) {
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

            String columnName1 = resultSetMetaData.getColumnName(1);
            String columnName2 = resultSetMetaData.getColumnName(2);
            String columnName3 = resultSetMetaData.getColumnName(3);
            String columnName4 = resultSetMetaData.getColumnName(4);

            assertEquals("student_id", columnName1);
            assertEquals("first_name", columnName2);
            assertEquals("last_name", columnName3);
            assertEquals("group_id", columnName4);
        }
    }

    @Test
    void testOnCreateGroupTable() throws SQLException {
        String sql = "SELECT group_id, group_name, size FROM groups";
        try (Connection connection = util.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);) {
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

            String columnName1 = resultSetMetaData.getColumnName(1);
            String columnName2 = resultSetMetaData.getColumnName(2);
            String columnName3 = resultSetMetaData.getColumnName(3);

            assertEquals("group_id", columnName1);
            assertEquals("group_name", columnName2);
            assertEquals("size", columnName3);
        }
    }

    @Test
    void testOnCreateCourseTable() throws SQLException {
        String sql = "SELECT course_id, course_name, course_description FROM courses";
        try (Connection connection = util.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);) {
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

            String columnName1 = resultSetMetaData.getColumnName(1);
            String columnName2 = resultSetMetaData.getColumnName(2);
            String columnName3 = resultSetMetaData.getColumnName(3);

            assertEquals("course_id", columnName1);
            assertEquals("course_name", columnName2);
            assertEquals("course_description", columnName3);
        }
    }

    @Test
    void testOnCreateStudentCourseTable() throws SQLException {
        String sql = "SELECT student_id, course_id FROM students_courses";
        try (Connection connection = util.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);) {
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

            String columnName1 = resultSetMetaData.getColumnName(1);
            String columnName2 = resultSetMetaData.getColumnName(2);

            assertEquals("student_id", columnName1);
            assertEquals("course_id", columnName2);
        }
    }
}
