package ru.fedormakarov.foxminded.task7.sql.businesslogic;

import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import ru.fedormakarov.foxminded.task7.sql.entity.Course;
import ru.fedormakarov.foxminded.task7.sql.entity.Group;
import ru.fedormakarov.foxminded.task7.sql.entity.Student;
import ru.fedormakarov.foxminded.task7.sql.entity.StudentCourse;
import ru.fedormakarov.foxminded.task7.sql.service.CourseService;
import ru.fedormakarov.foxminded.task7.sql.service.GroupSevice;
import ru.fedormakarov.foxminded.task7.sql.service.StudentCourseService;
import ru.fedormakarov.foxminded.task7.sql.service.StudentService;

public class TableBinder {
    private static final int MIN_STUDENTS = 10;
    private static final int MAX_STUDENTS = 30;
    private static final int MAX_COURSES = 3;
    private static final int ALL_STUDENTS = 200;

    private GroupSevice groupSevice = new GroupSevice();
    private StudentService studentService = new StudentService();
    private CourseService courseService = new CourseService();
    private StudentCourseService studentCourseService = new StudentCourseService();

    public void bindTable() {
        assignStudentToGroup();
        assignStudentToCourses();
        System.out.println("Binding succesfull!");
    }

    private void assignStudentToGroup() {
        List<Group> groupListWithoutSize = groupSevice.getAll();
        List<Group> groupListWithSize = generateSizeToGroups(groupListWithoutSize);
        LinkedList<Student> studentList = new LinkedList<>(studentService.getAll());
        Collections.shuffle(studentList);
        for (Group group : groupListWithSize) {
            if (group.getSize() == 0) {
                break;
            }
            int groupId = group.getGroupId();
            for (int i = 0; i < group.getSize(); i++) {
                Student student = studentList.pollFirst();
                student.setGroupId(groupId);
                studentService.update(student);
            }
        }

    }

    private List<Group> generateSizeToGroups(List<Group> inputGroupList) {
        int totalSize = 0;
        Random random = new Random();
        for (Group group : inputGroupList) {
            if (totalSize == ALL_STUDENTS || totalSize > 191) {
                break;
            }
            int randomGroupSize = MIN_STUDENTS + random.nextInt(MAX_STUDENTS - MIN_STUDENTS + 1);
            group.setSize(randomGroupSize);
            totalSize += randomGroupSize;
            groupSevice.update(group);
        }
        return inputGroupList;
    }

    private void assignStudentToCourses() {
        List<Student> studentList = studentService.getAll();
        List<Course> courseList = courseService.getAll();

        Random random = new Random();

        for (Student student : studentList) {
            int randomCountCourse = random.nextInt(MAX_COURSES) + 1;
            for (int i = 0; i < randomCountCourse; i++) {
                Course randomCourse = courseList.get(random.nextInt(courseList.size()));
                student.getCourses().add(randomCourse);
            }
            addStudentCoursesToTable(student);
        }
    }

    private void addStudentCoursesToTable(Student student) {
        for (Course course : student.getCourses()) {
            StudentCourse studentCourse = new StudentCourse();
            studentCourse.setStudentId(student.getStudentId());
            studentCourse.setCourseId(course.getCourseId());
            studentCourseService.save(studentCourse);
        }
    }
}