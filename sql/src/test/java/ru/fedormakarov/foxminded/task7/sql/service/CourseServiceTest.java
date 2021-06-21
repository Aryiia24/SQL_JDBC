package ru.fedormakarov.foxminded.task7.sql.service;

import static org.junit.jupiter.api.Assertions.*;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import ru.fedormakarov.foxminded.task7.sql.entity.Course;

@TestMethodOrder(OrderAnnotation.class)
class CourseServiceTest {
    private final CourseService courseService = new CourseService();

    @Test
    @Order(1)
    void testAddCourse() throws SQLException {
        Course course = new Course();
        course.setCourseId(11);
        course.setCourseName("TestCourseName");
        course.setCourseDescription("Test course description");
        assertTrue(courseService.add(course));
    }

    @Test
    @Order(2)
    void testUpdateMethod() throws SQLException {
        Course course = new Course();
        course.setCourseId(11);
        course.setCourseName("TestCourseName! ");
        course.setCourseDescription("Test course description!");
        assertTrue(courseService.update(course));
    }

    @Test
    @Order(4)
    void testGetByIdMethod() throws SQLException {
        int course_id = 1;
        Course course = courseService.getById(course_id);
        assertNotNull(course);
        assertEquals(course_id, course.getCourseId());
    }

    @Test
    @Order(3)
    void testDeleteMethod() throws SQLException {
        assertTrue(courseService.delete(11));
    }

    @Test
    @Order(5)
    void testGetAllCoursesMethod() throws SQLException {
        List<Course> courseList = courseService.getAll();
        assertNotNull(courseList);
        assertEquals(10, courseList.size());
    }
}