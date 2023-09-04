package org.example;

import lombok.AllArgsConstructor;

import org.example.utils.SpringScriptUtility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Hello world!
 */
public class App {
    private Boolean exit;
    private Controller controller;
    private View view;

    public App() {
        createAndInitDb();
        view = new View();
        controller = new Controller(view);
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
//        DbInitializer dbInitializer = new DbInitializer();
//        dbInitializer.initDb();
    }

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    public void run() {
        view.printMsg("Welcome to Console Application type EXIT to quit application");
        while (exit) {
            view.showAllActions();
            getUserCommand();
        }
    }

    public void getUserCommand() {
        String command = view.requestString();
        switch (command) {
            case "a":
                controller.getAllGroupWithLessOrEqualStudentsCommand();
                break;
            case "b":
                controller.getStudentByCourseAndName();
                break;
            case "c":
                controller.createNewStudent();
                break;
            case "d":
                controller.deleteStudent();
                break;
            case "e":
                controller.assignStudentToCourse();
                break;
            case "f":
                controller.removeStudentFromCourse();
                break;
            case "x":
                exit = false;
                break;
            default:
                view.printMsg("Wrong command.");
                break;
        }
        view.printMsg("Completed!");
    }


}
