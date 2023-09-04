package org.example;

import lombok.RequiredArgsConstructor;
import org.example.dao.CourseDAO;
import org.example.dao.GroupDAO;
import org.example.dao.StudentDAO;
import org.example.model.Student;

@RequiredArgsConstructor
public class Controller {

    private StudentDAO studentDAO = new StudentDAO();
    private CourseDAO courseDAO = new CourseDAO();
    private GroupDAO groupDAO = new GroupDAO();

    private final View view;

    public void getAllGroupWithLessOrEqualStudentsCommand() {
        view.printMsg("Type number of students:");
        int studentNumber = view.requestInt();
        groupDAO.findAllHaveCertainAmountOfStudents(studentNumber).forEach(System.out::println);
    }

    public void getStudentByCourseAndName() {
        view.printMsg("Type course name:");
        String courseName = view.requestString();
        view.printMsg("Type student name:");
        String studentName = view.requestString();
        studentDAO.findAllStudentsByCourseAndByName(courseName, studentName).forEach(System.out::println);

    }

    public void createNewStudent() {
        view.printMsg("Type student group id:");
        int groupId = view.requestInt();
        view.printMsg("Type student first name:");
        String firstName = view.requestString();
        view.printMsg("Type student last name:");
        String lastName = view.requestString();
        studentDAO.addStudent(new Student(groupId, firstName, lastName));
    }

    public void deleteStudent() {
        view.printMsg("Type student id:");
        int studentId = view.requestInt();
        studentDAO.deleteStudent(studentId);
    }

    public void assignStudentToCourse() {
        view.printMsg("Type student id:");
        int studentId = view.requestInt();
        courseDAO.getAllCourses().forEach(System.out::println);
        view.printMsg("Type course id:");
        int courseId = view.requestInt();
        studentDAO.assignCourseToStudentById(courseId, studentId);
    }

    public void removeStudentFromCourse(){
        view.printMsg("Type student id:");
        int studentId = view.requestInt();
        view.printMsg("Type course id to be removed:");
        int courseId = view.requestInt();
        studentDAO.removeStudentFromCourse(courseId, studentId);
    }


}



