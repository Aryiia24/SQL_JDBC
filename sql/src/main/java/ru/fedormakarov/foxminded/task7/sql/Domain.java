package ru.fedormakarov.foxminded.task7.sql;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import ru.fedormakarov.foxminded.task7.sql.businesslogic.TableBinder;
import ru.fedormakarov.foxminded.task7.sql.businesslogic.TableCreator;

import ru.fedormakarov.foxminded.task7.sql.entity.Student;
import ru.fedormakarov.foxminded.task7.sql.entity.StudentCourse;

import ru.fedormakarov.foxminded.task7.sql.service.CourseService;
import ru.fedormakarov.foxminded.task7.sql.service.GroupSevice;
import ru.fedormakarov.foxminded.task7.sql.service.StudentCourseService;
import ru.fedormakarov.foxminded.task7.sql.service.StudentService;

public class Domain {

    private static final String SEPARATOR = "\r\n";

    public static void main(String[] args) throws SQLException {

        TableCreator tableCreator = new TableCreator();
        tableCreator.createAndFillTables("createTables.sql");

        TableBinder tableBinder = new TableBinder();
        tableBinder.bindTable();
        Domain domain = new Domain();
        domain.menu();
    }

    private void menu() {
        String selection;
        final GroupSevice groupService = new GroupSevice();
        final StudentService studentService = new StudentService();
        final CourseService courseService = new CourseService();
        final StudentCourseService studentCourseService = new StudentCourseService();
        try (Scanner input = new Scanner(System.in);) {
            printMenu();
            selection = input.nextLine();

            switch (selection) {

            case "a":
                System.out.println("Enter the number of students in group (at least 10):");
                int countStudent = input.nextInt();
                List<String> resultList = groupService.getGroupsWithLessStudentCount(countStudent).stream()
                        .map(g -> g.toString()).collect(Collectors.toList());
                System.out.println(String.join(SEPARATOR, resultList));
                menu();
                break;

            case "b":
                System.out.println("Enter course name:");
                String courseName = input.nextLine();
                studentService.getAllStudentsFromCourse(courseName);
                menu();
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
                menu();

                break;

            case "d":
                System.out.println("Enter student id:");
                int studentIdForDelete = input.nextInt();
                studentService.delete(studentIdForDelete);
                System.out.println("Student was deleted");
                menu();
                break;

            case "e":

                List<String> studentListString = studentService.getAll().stream().map(s -> s.toString())
                        .collect(Collectors.toList());
                System.out.println(String.join(SEPARATOR, studentListString));

                System.out.println("Enter student id:");
                int studentIdForAddCourse = input.nextInt();

                System.out.println("Courses that the student already has");
                List<String> studentCourseListAlreadyHas = courseService
                        .getListCoursesByStudentCourses(
                                studentCourseService.getListStudentCoursesByStudentId(studentIdForAddCourse))
                        .stream().map(st -> st.toString()).collect(Collectors.toList());
                System.out.println(String.join(SEPARATOR, studentCourseListAlreadyHas));
                System.out.println(SEPARATOR);
                System.out.println("All courses:");
                List<String> allCourses = courseService.getAll().stream().map(c -> c.toString())
                        .collect(Collectors.toList());
                System.out.println(String.join(SEPARATOR, allCourses));
                System.out.println(SEPARATOR);
                System.out.println("Enter the course id where you want to add the student:");
                int courseId = input.nextInt();
                StudentCourse studentCourse = new StudentCourse();
                studentCourse.setStudentId(studentIdForAddCourse);
                studentCourse.setCourseId(courseId);
                studentCourseService.save(studentCourse);
                System.out.println("Student was added to the course.");
                menu();
                break;

            case "f":
                System.out.println("Enter student id: ");
                int studentIdForDeleteCourse = input.nextInt();
                System.out.println("Student courses: ");
                List<String> studentCoursesList = courseService
                        .getListCoursesByStudentCourses(
                                studentCourseService.getListStudentCoursesByStudentId(studentIdForDeleteCourse))
                        .stream().map(stc -> stc.toString()).collect(Collectors.toList());
                System.out.println(String.join(SEPARATOR, studentCoursesList));
                System.out.println(SEPARATOR);
                System.out.println("Enter the course id that you want to remove from the student:");
                int courseIdForDelete = input.nextInt();
                studentCourseService.removeStudentCourse(studentIdForDeleteCourse, courseIdForDelete);
                System.out.println("Done");
                List<String> courseAfterDelete = courseService
                        .getListCoursesByStudentCourses(
                                studentCourseService.getListStudentCoursesByStudentId(studentIdForDeleteCourse))
                        .stream().map(course -> course.toString()).collect(Collectors.toList());
                System.out.println(String.join(SEPARATOR, courseAfterDelete));
                menu();
                break;

            case "ex":
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
        System.out.println("ex. Close programm.");
        System.out.print("Enter comand:");
    }

}