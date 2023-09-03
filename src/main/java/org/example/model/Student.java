package org.example.model;

import lombok.Data;

@Data
public class Student {
    private int studentId;
    private int groupId;
    private String firstName;
    private String lastName;

    public Student(int groupId, String firstName, String lastName) {
        this.groupId = groupId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Student(int studentId, int groupId, String firstName, String lastName) {
        this.studentId = studentId;
        this.groupId = groupId;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
