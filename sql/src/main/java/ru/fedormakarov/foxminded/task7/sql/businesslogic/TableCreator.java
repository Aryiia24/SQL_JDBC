package ru.fedormakarov.foxminded.task7.sql.businesslogic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.apache.ibatis.jdbc.ScriptRunner;

import ru.fedormakarov.foxminded.task7.sql.entity.Course;
import ru.fedormakarov.foxminded.task7.sql.entity.Group;
import ru.fedormakarov.foxminded.task7.sql.entity.Student;
import ru.fedormakarov.foxminded.task7.sql.service.CourseService;
import ru.fedormakarov.foxminded.task7.sql.service.GroupSevice;
import ru.fedormakarov.foxminded.task7.sql.service.StudentService;

public class TableCreator {

    private static final int COUNT_GROUPS = 10;
    private static final int COUNT_STUDENTS = 200;
    private static final int COUNT_COURSES = 10;
    private static final char HYPHEN = '-';
    private static final int MAX_VALUE = 100;
    private static final int MIN_VALUE = 11;
    private static final int COUNT_OF_LETTERS = 26;

    public void createAndFillTables(String SQLScriptFileName) {
        createEmptyTables(SQLScriptFileName);
        List<Student> students = generateListRandomStudents();
        List<Group> groups = generateListRandomGroups();
        List<Course> courses = generateListCourses();
        fillStudentTable(students);
        fillGroupTable(groups);
        fillCourseTable(courses);
        System.out.println("Tables were created!");
    }

    private void fillStudentTable(List<Student> students) {
        StudentService studentService = new StudentService();
        for (Student student : students) {
            studentService.save(student);
        }
    }

    private void fillGroupTable(List<Group> groups) {
        GroupSevice groupSevice = new GroupSevice();
        for (Group group : groups) {

            groupSevice.save(group);

        }
    }

    private void fillCourseTable(List<Course> courses) {
        CourseService courseService = new CourseService();
        for (Course course : courses) {
            courseService.save(course);
        }
    }

    private List<Course> generateListCourses() {
        List<Course> listCourses = new ArrayList<>(COUNT_COURSES);
        Map<String, String> courses = new HashMap<>();
        courses.put("Math", "Math course");
        courses.put("Biology", "Biology course");
        courses.put("Chemistry", "Chemistry course");
        courses.put("Computer science", "Computer science course");
        courses.put("English", "English course");
        courses.put("History", "History course");
        courses.put("Physics", "Physics course");
        courses.put("Economics", "Economics course");
        courses.put("Technical drawing", "Technical drawing course");
        courses.put("Astronomy", "Astronomy course");
        int counter = 1;
        for (Map.Entry<String, String> entry : courses.entrySet()) {
            Course course = new Course();
            course.setCourseId(counter++);
            course.setCourseName(entry.getKey());
            course.setCourseDescription(entry.getValue());
            listCourses.add(course);
        }
        return listCourses;
    }

    private List<Group> generateListRandomGroups() {
        List<Group> groups = new ArrayList<>();
        for (int i = 0; i < COUNT_GROUPS; i++) {
            Group group = new Group();
            group.setGroupId(i + 1);
            group.setGroupName(generateGroupName());
            groups.add(group);
        }
        return groups;

    }

    private List<Student> generateListRandomStudents() {
        List<Student> students = new ArrayList<Student>();
        Random random = new Random();

        String[] names = { "Fedor", "Yaroslav", "Konstantin", "Andrey", "Vasislisa", "Alisa", "Nikita", "Sergey",
                "Maxim", "Stepan", "Eva", "Ekaterina", "Artem", "Marsel", "Elina", "Olga", "Ilya", "Arina", "Alena",
                "Zhenya" };
        String[] lastNames = { "Ball", "White", "Hayes", "Shaw", "King", "Jackson", "Ford", "Allen", "Mitchell",
                "Washington", "Campbell", "Smith", "Dawson", "Carlson", "Cox", "Boyd", "Rogers", "Smith", "Thompson",
                "Morris" };

        List<String> listNames = Arrays.asList(names);
        List<String> listLastNames = Arrays.asList(lastNames);
        Collections.shuffle(listNames);
        Collections.shuffle(listLastNames);
        for (int i = 0; i < COUNT_STUDENTS; i++) {
            Student student = new Student();
            student.setFirstName(listNames.get(random.nextInt(20)));
            student.setLastName(listLastNames.get(random.nextInt(20)));
            student.setStudentId(i + 1);
            students.add(student);
        }
        return students;
    }

    private String generateGroupName() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        char firstCharacter = (char) (random.nextInt(COUNT_OF_LETTERS) + 'a');
        char secondCharacter = (char) (random.nextInt(COUNT_OF_LETTERS) + 'a');
        sb.append(firstCharacter);
        sb.append(secondCharacter);
        sb.append(HYPHEN);
        sb.append(random.nextInt(MAX_VALUE - MIN_VALUE) + MIN_VALUE);
        return sb.toString();
    }

    private void createEmptyTables(String SQLScriptFileName) {

        try {
            Connection connection = DatabaseConnector.getInstance().getConnection();
            ScriptRunner scriptRunner = new ScriptRunner(connection);
            ClassLoader loader = this.getClass().getClassLoader();
            File sqlScript = new File(loader.getResource(SQLScriptFileName).getFile());
            Reader reader = new BufferedReader(new FileReader(sqlScript));
            scriptRunner.runScript(reader);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Script file not found", e);
        }
    }

}
