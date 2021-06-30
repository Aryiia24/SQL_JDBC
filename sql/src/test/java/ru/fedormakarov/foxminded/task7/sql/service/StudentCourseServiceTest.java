package ru.fedormakarov.foxminded.task7.sql.service;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import ru.fedormakarov.foxminded.task7.sql.entity.StudentCourse;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;

@TestMethodOrder(OrderAnnotation.class)
class StudentCourseServiceTest {

    private StudentCourseService studentCourseService = new StudentCourseService();

    @Test
    @Order(1)
    void testAddStudentCourseMethod() {
        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setStudentId(3);
        studentCourse.setCourseId(4);
        assertTrue(studentCourseService.save(studentCourse));
    }

    @Test
    @Order(2)
    void testRemoveStudentCourseMethod() {
        assertTrue(studentCourseService.removeStudentCourse(3, 4));
    }

    @Test
    @Order(3)
    void testGetAllStudentCourse() throws SQLException {
        List<StudentCourse> studentCourseList = studentCourseService.getAll();
        assertNotNull(studentCourseList);
    }

    @Test
    @Order(4)
    void testGetListSudentCoursesByStudentIdMethod() throws SQLException {
        int studentId = 5;
        List<StudentCourse> studentCourseList = studentCourseService.getListStudentCoursesByStudentId(studentId);
        assertNotNull(studentCourseList);
        for (StudentCourse studentCourse : studentCourseList) {
            assertEquals(studentId, studentCourse.getStudentId());
        }

    }
}
