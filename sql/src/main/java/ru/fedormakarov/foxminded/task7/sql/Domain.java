package ru.fedormakarov.foxminded.task7.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import ru.fedormakarov.foxminded.task7.sql.businesslogic.TableBinder;
import ru.fedormakarov.foxminded.task7.sql.businesslogic.TableCreator;
import ru.fedormakarov.foxminded.task7.sql.businesslogic.DatabaseConnector;
import ru.fedormakarov.foxminded.task7.sql.entity.Student;
import ru.fedormakarov.foxminded.task7.sql.entity.StudentCourse;
import ru.fedormakarov.foxminded.task7.sql.formatter.CourseFormatter;
import ru.fedormakarov.foxminded.task7.sql.formatter.GroupFormatter;
import ru.fedormakarov.foxminded.task7.sql.formatter.StudentFormatter;
import ru.fedormakarov.foxminded.task7.sql.service.CourseService;
import ru.fedormakarov.foxminded.task7.sql.service.GroupSevice;
import ru.fedormakarov.foxminded.task7.sql.service.StudentCourseService;
import ru.fedormakarov.foxminded.task7.sql.service.StudentService;

public class Domain {

    public static void main(String[] args) throws SQLException {

        TableCreator tableCreator = new TableCreator();
        tableCreator.createAndFillTables("createTables.sql");

        TableBinder tableBinder = new TableBinder();
        tableBinder.bindTable();

        GroupSevice groupService = new GroupSevice();
        StudentService studentService = new StudentService();
        CourseService courseService = new CourseService();
        StudentCourseService studentCourseService = new StudentCourseService();
        StudentFormatter studentFormatter = new StudentFormatter();
        GroupFormatter groupFormatter = new GroupFormatter();
        CourseFormatter courseFormatter = new CourseFormatter();

        String selection;
        try (Scanner input = new Scanner(System.in); Connection connection = DatabaseConnector.getInstance().getConnection()) {
            printMenu();
            selection = input.nextLine();

            switch (selection) {

            case "a":
                System.out.println("Enter the number of students in group (at least 10):");
                int countStudent = input.nextInt();
                groupFormatter.showGroups(groupService.getGroupsWithLessStudentCount(countStudent));
                break;

            case "b":
                System.out.println("Enter course name:");
                String courseName = input.nextLine();
                studentFormatter.showStudents(studentService.getAllStudentsFromCourse(courseName));
                break;

            case "c":
                Student student = new Student();

                System.out.println("Enter student_id (at least 200):");
                int studentIdForAdd = input.nextInt();
                student.setStudentId(studentIdForAdd);

                System.out.println("Enter first name student:");
                String firstName = input.nextLine();
                student.setFirstName(firstName);

                System.out.println("Enter last name student:");
                String lastName = input.nextLine();
                student.setLastName(lastName);

                studentService.save(student);
                System.out.println("Student was added");
                break;

            case "d":
                System.out.println("Enter student id:");
                int studentIdForDelete = input.nextInt();
                studentService.delete(studentIdForDelete);
                System.out.println("Student was deleted");
                break;

            case "e":
                studentFormatter.showStudents(studentService.getAll());

                System.out.println("Enter student id:");
                int studentIdForAddCourse = input.nextInt();

                System.out.println("Courses that the student already has");
                courseFormatter.showCourses(courseService.getListCoursesByStudentCourses(
                        studentCourseService.getListStudentCoursesByStudentId(studentIdForAddCourse)));
                System.out.println("\r\n");
                System.out.println("All courses:");
                courseFormatter.showCourses(courseService.getAll());
                System.out.println("\r\n");
                System.out.println("Enter the course id where you want to add the student:");
                int courseId = input.nextInt();
                StudentCourse studentCourse = new StudentCourse();
                studentCourse.setStudentId(studentIdForAddCourse);
                studentCourse.setCourseId(courseId);
                studentCourseService.save(studentCourse);
                System.out.println("Student was added to the course.");
                break;

            case "f":
                System.out.println("Enter student id: ");
                int studentIdForDeleteCourse = input.nextInt();
                System.out.println("Student courses: ");
                courseFormatter.showCourses(courseService.getListCoursesByStudentCourses(
                        studentCourseService.getListStudentCoursesByStudentId(studentIdForDeleteCourse)));
                System.out.println("\r\n");
                System.out.println("Enter the course id that you want to remove from the student:");
                int courseIdForDelete = input.nextInt();
                studentCourseService.removeStudentCourse(studentIdForDeleteCourse, courseIdForDelete);
                System.out.println("Done");
                courseFormatter.showCourses(courseService.getListCoursesByStudentCourses(
                        studentCourseService.getListStudentCoursesByStudentId(studentIdForDeleteCourse)));
                connection.close();
                break;
            }
        }
    }

    private static void printMenu() {
        System.out.println("a. Find all groups with less or equals student count");
        System.out.println("b. Find all students related to course with given name");
        System.out.println("c. Add new student");
        System.out.println("d. Delete student by STUDENT_ID");
        System.out.println("e. Add a student to the course (from a list)");
        System.out.println("f. Remove the student from one of his or her courses");
        System.out.print("Enter comand:");
    }
}