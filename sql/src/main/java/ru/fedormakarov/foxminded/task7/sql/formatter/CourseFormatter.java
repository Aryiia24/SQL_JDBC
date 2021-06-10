package ru.fedormakarov.foxminded.task7.sql.formatter;

import java.util.List;

import ru.fedormakarov.foxminded.task7.sql.entity.Course;

public class CourseFormatter {
    public void showCourses(List<Course> courseList) {
        for (Course course : courseList) {
            System.out.println(course);
        }
    }
}
