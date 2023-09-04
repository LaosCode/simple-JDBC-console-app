package org.example;

import java.util.Scanner;

public class View {
    private Scanner scanner = new Scanner(System.in);

    public void printMsg(String text) {
        System.out.println(text);
    }

    public int requestInt() {
        return scanner.nextInt();
    }
    public String requestString() {
        return scanner.next();
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
}
