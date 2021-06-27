package ru.fedormakarov.foxminded.task7.sql;

import java.sql.SQLException;
import java.util.Scanner;
import ru.fedormakarov.foxminded.task7.sql.businesslogic.TableBinder;
import ru.fedormakarov.foxminded.task7.sql.businesslogic.TableCreator;
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
    private static final GroupSevice GROUP_SEVICE = new GroupSevice();
    private static final StudentService STUDENT_SERVICE = new StudentService();
    private static final CourseService COURSE_SERVICE = new CourseService();
    private static final StudentCourseService STUDENT_COURSE_SERVICE = new StudentCourseService();
    private static final StudentFormatter STUDENT_FORMATTER = new StudentFormatter();
    private static final GroupFormatter GROUP_FORMATTER = new GroupFormatter();
    private static final CourseFormatter COURSE_FORMATTER = new CourseFormatter();

    public static void main(String[] args) throws SQLException {

        TableCreator tableCreator = new TableCreator();
        tableCreator.createAndFillTables("createTables.sql");
        TableBinder tableBinder = new TableBinder();
        tableBinder.BindTable();

        String selection;
        try (Scanner input = new Scanner(System.in)) {
            printMenu();
            selection = input.nextLine();

            switch (selection) {

            case "a":
                System.out.println("Enter the number of students in group (at least 10):");
                int countStudent = input.nextInt();
                GROUP_FORMATTER.showGroups(GROUP_SEVICE.getGroupsWithLessStudentCount(countStudent));
                break;

            case "b":
                System.out.println("Enter course name:");
                String courseName = input.nextLine();
                STUDENT_FORMATTER.showStudents(STUDENT_SERVICE.getAllStudentsFromCourse(courseName));
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

                STUDENT_SERVICE.add(student);
                System.out.println("Student was added");
                break;

            case "d":
                System.out.println("Enter student id:");
                int studentIdForDelete = input.nextInt();
                STUDENT_SERVICE.delete(studentIdForDelete);
                System.out.println("Student was deleted");
                break;

            case "e":
                STUDENT_FORMATTER.showStudents(STUDENT_SERVICE.getAll());

                System.out.println("Enter student id:");
                int studentIdForAddCourse = input.nextInt();

                System.out.println("Courses that the student already has");
                COURSE_FORMATTER.showCourses(COURSE_SERVICE.getListCoursesByStudentCourses(
                        STUDENT_COURSE_SERVICE.getListStudentCoursesByStudentId(studentIdForAddCourse)));
                System.out.println("\r\n");
                System.out.println("All courses:");
                COURSE_FORMATTER.showCourses(COURSE_SERVICE.getAll());
                System.out.println("\r\n");
                System.out.println("Enter the course id where you want to add the student:");
                int courseId = input.nextInt();
                StudentCourse studentCourse = new StudentCourse();
                studentCourse.setStudentId(studentIdForAddCourse);
                studentCourse.setCourseId(courseId);
                STUDENT_COURSE_SERVICE.add(studentCourse);
                System.out.println("Student was added to the course.");
                break;

            case "f":
                System.out.println("Enter student id: ");
                int studentIdForDeleteCourse = input.nextInt();
                System.out.println("Student courses: ");
                COURSE_FORMATTER.showCourses(COURSE_SERVICE.getListCoursesByStudentCourses(
                        STUDENT_COURSE_SERVICE.getListStudentCoursesByStudentId(studentIdForDeleteCourse)));
                System.out.println("\r\n");
                System.out.println("Enter the course id that you want to remove from the student:");
                int courseIdForDelete = input.nextInt();
                STUDENT_COURSE_SERVICE.removeStudentCourse(studentIdForDeleteCourse, courseIdForDelete);
                System.out.println("Done");
                COURSE_FORMATTER.showCourses(COURSE_SERVICE.getListCoursesByStudentCourses(
                        STUDENT_COURSE_SERVICE.getListStudentCoursesByStudentId(studentIdForDeleteCourse)));
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