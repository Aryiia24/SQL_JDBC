package ru.fedormakarov.foxminded.task7.sql.service;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import ru.fedormakarov.foxminded.task7.sql.entity.Student;

@TestMethodOrder(OrderAnnotation.class)
class StudentServiceTest {

    private final StudentService studentService = new StudentService();

    @Test
    @DisplayName("Method StudentService.getById() should return Student")
    @Order(1)
    void testGetStudentByIdMethod() throws SQLException {
        Student student = studentService.getById(1);
        assertEquals(Student.class, student.getClass());
        assertNotNull(student);
    }

    @Test
    @Order(2)
    void testAddStudentMethod() throws SQLException {
        Student student = new Student();
        student.setStudentId(201);
        student.setFirstName("TestName");
        student.setLastName("TestLastName");
        assertTrue(studentService.add(student));
        Student actualStudent = studentService.getById(201);
        assertEquals(student, actualStudent);
    }

    @Test
    @Order(3)
    void testUpdateStudentMethod() throws SQLException {
        Student student = new Student();
        student.setStudentId(201);
        student.setFirstName("TestName1");
        student.setLastName("TestLastName2");
        assertTrue(studentService.update(student));
        Student updatedStudent = studentService.getById(201);
        assertEquals(student, updatedStudent);
    }

    @Test
    @Order(4)
    void testDeleteMethod() throws SQLException {
        assertTrue(studentService.delete(201));
    }

    @Test
    @Order(5)
    void testGetAllMethod() throws SQLException {
        List<Student> studentList = studentService.getAll();
        assertEquals(200, studentList.size());
        assertNotNull(studentList);
    }

    @Test
    @Order(6)
    void testgetAllStudentsFromCourseMethod() throws SQLException {
        String courseName = "Math";
        List<Student> studentFromCourse = studentService.getAllStudentsFromCourse(courseName);
        assertNotNull(studentFromCourse);
    }
}
