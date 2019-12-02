package db;

import model.*;

import java.sql.SQLException;
import java.util.*;

public class DBtest {
    public static void main(String[] args) throws SQLException {
        int code = StudentDAO.getInstance().addStudent("Jerry", "", "Tom", "U28384838", "graduate", "", "CS591P1EEE");
        System.out.println(code);
        Student student = StudentDAO.getInstance().getStudent("U28384838");
        System.out.println(student);
        student.setComment("Good Boy");
        code = StudentDAO.getInstance().updateStudent(student);
        student = StudentDAO.getInstance().getStudent("U28384838");
        System.out.println(student);
        StudentDAO.getInstance().freezeStudent("U28384838", "CS591P1EEE");
        StudentDAO.getInstance().deleteStudent("U28384838", "CS591P1EEE");
    }
}
