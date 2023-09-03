package org.example;

import org.example.dao.CourseDAO;
import org.example.dao.GroupDAO;
import org.example.dao.StudentDAO;
import org.example.model.Student;
import org.example.utils.SpringScriptUtility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Hello world!
 */
public class App {
    private StudentDAO studentDAO = new StudentDAO();
    private CourseDAO courseDAO = new CourseDAO();
    private GroupDAO groupDAO = new GroupDAO();
    private Boolean exit;
    Scanner scanner;

    public App() {
        scanner = new Scanner(System.in);
        createAndInitDb();
        exit = true;
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(Constants.DB_URL, Constants.USER, Constants.PASS);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void createAndInitDb() {
        Connection connection = getConnection();
        try {
            SpringScriptUtility.runScript(Constants.PATH_TO_SCRIPT + "1_remove_db.sql", connection);
            SpringScriptUtility.runScript(Constants.PATH_TO_SCRIPT + "2_create_db.sql", connection);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        DbInitializer dbInitializer = new DbInitializer();
        dbInitializer.initDb();
    }

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    public void run() {
        printMsg("Welcome to Console Application type EXIT to quit application");
        while (exit) {
            showAllActions();
            getUserCommand();
        }
    }

    public void showAllActions() {
        printMsg("Choose your action:");
        printMsg("a. Find all groups with less or equal students’ number+\n" +
                "b. Find all students related to the course with the given name+\n" +
                "c. Add a new student+\n" +
                "d. Delete a student by the STUDENT_ID+\n" +
                "e. Add a student to the course (from a list)+\n" +
                "f. Remove the student from one of their courses.+\n" +
                "x. Exit.");
    }

    public void getUserCommand() {
        String command = scanner.next();
        switch (command) {
            case "a":
                printMsg("Type number of students:");
                int studentNumber = scanner.nextInt();
                groupDAO.findAllGroupWithLessOrEqualStudents(studentNumber).forEach(System.out::println);
                break;
            case "b":
                printMsg("Type course name:");
                String courseName = scanner.next();
                printMsg("Type student name:");
                String studentName = scanner.next();
                studentDAO.findAllStudentsByCourseAndByName(courseName, studentName).forEach(System.out::println);
                break;
            case "c":
                printMsg("Type student group id:");
                int groupId = scanner.nextInt();
                printMsg("Type student first name:");
                String firstName = scanner.next();
                printMsg("Type student last name:");
                String lastName = scanner.next();
                studentDAO.addStudent(new Student(groupId, firstName, lastName));
                break;
            case "d":
                printMsg("Type student id:");
                int studentId = scanner.nextInt();
                studentDAO.deleteStudent(studentId);
                break;
            case "e":
                printMsg("Type student id:");
                studentId = scanner.nextInt();
                courseDAO.showAllCourses().forEach(System.out::println);
                printMsg("Type course id:");
                int courseId = scanner.nextInt();
                studentDAO.assignCourseToStudentById(courseId, studentId);
                break;
            case "f":
                printMsg("Type student id:");
                studentId = scanner.nextInt();
                printMsg("Type course id to be removed:");
                courseId = scanner.nextInt();
                studentDAO.removeStudentFromCourse(courseId, studentId);
                break;
            case "x":
                exit = false;
                break;
            default:
                printMsg("Wrong command.");
                break;
        }
        printMsg("Completed!");
    }

    public static void printMsg(String text) {
        System.out.println(text);
    }
}
