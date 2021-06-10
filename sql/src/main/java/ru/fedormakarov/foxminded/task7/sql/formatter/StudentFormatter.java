package ru.fedormakarov.foxminded.task7.sql.formatter;

import java.util.List;

import ru.fedormakarov.foxminded.task7.sql.entity.Student;

public class StudentFormatter {
    public void showStudents(List<Student> studentList) {
        for (Student student : studentList) {
            System.out.println(student);
        }
    }
}
