package org.example;

import lombok.NoArgsConstructor;
import org.example.dao.CourseDAO;
import org.example.dao.GroupDAO;
import org.example.dao.StudentDAO;
import org.example.model.Student;

import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
public class DbInitializer {
    private final StudentDAO studentDAO = new StudentDAO();
    private final GroupDAO groupDao = new GroupDAO();
    private final CourseDAO courseDAO = new CourseDAO();

    public void initDb() {
        List<String> groupNames = Arrays.asList(
                "AB-45",
                "XY-78",
                "CD-23",
                "EF-56",
                "GH-12",
                "IJ-34",
                "KL-67",
                "MN-89",
                "OP-01",
                "QR-78"
        );
        groupNames.forEach(group -> groupDao.addGroup(group));

        List<String> courses = Arrays.asList(
                "Mathematics",
                "English Language Arts",
                "Science",
                "History",
                "Foreign Languages",
                "Physical Education",
                "Social Studies",
                "Art",
                "Music",
                "Computer Science"
        );
        courses.forEach(course -> courseDAO.addCourse(course, course + "-desccription"));

        List<String> firstNamesList = Arrays.asList(
                "Aiden",
                "Madison",
                "Oliver",
                "Ella",
                "Jackson",
                "Grace",
                "Caleb",
                "Lily",
                "Lucas",
                "Avery",
                "Mia",
                "Henry",
                "Sophia",
                "Sebastian",
                "Charlotte",
                "Elijah",
                "Emma",
                "Mason",
                "Aria",
                "Scarlett"
        );
        List<String> lastNameList = Arrays.asList(
                "Anderson",
                "Smith",
                "Johnson",
                "Williams",
                "Brown",
                "Davis",
                "Jones",
                "Miller",
                "Wilson",
                "Taylor",
                "Martinez",
                "Harris",
                "Jackson",
                "Clark",
                "Young",
                "Turner",
                "Walker",
                "White",
                "Hall",
                "Lewis"
        );
        for (int i = 0; i < 200; i++) {
            studentDAO.addStudent(
                    new Student(
                            generateRandomInt(10) + 1,
                            firstNamesList.get(generateRandomInt(20)),
                            lastNameList.get(generateRandomInt(20))));
        }

        for (int i = 0; i < 200; i++) {
            studentDAO.assignCourseToStudentById(generateRandomInt(10) + 1, generateRandomInt(200) + 1);
        }
    }
    private int generateRandomInt(int number) {
        return (int) ((Math.random() * number));
    }


}


