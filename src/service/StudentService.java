package service;

import model.*;
import utils.Config;
import utils.ErrCode;

import java.util.HashMap;
import java.util.Map;

public class StudentService {
    private static StudentService instance = new StudentService();
    public CourseService courseService = CourseService.getInstance();

    private StudentService() {

    }

    public static StudentService getInstance() {
//        if (instance == null) {
//            instance = new StudentService();
//        }
        return instance;
    }

    public Map<String, Student> importStudent(String filename) {
        Map<String, Student> students = new HashMap<>();
        // TODO: read students from worksheet
        // TODO: add students to DB
        return students;
    }

    public int addStudent(String firstname, String midname, String lastname, String buid, String year, String comment, String courseId) {
        Name name = new Name(firstname, midname, lastname);
        Student student;
        // TODO: add name to DB
        if (year.equalsIgnoreCase("undergraduate")) {
            student = new UndergraduateStudent(name, buid, comment);
        }
        else {
            student = new GraduateStudent(name, buid, comment);
        }
        // TODO: add student to DB
        Course course = courseService.getCourse(courseId);
        Map<String, Student> students = course.getStudents();
        if (students.containsKey(buid)) {
            return ErrCode.STUDENTEXIST.getCode();
        }
        students.put(buid, student);
        course.setStudents(students);
        // TODO: update course in DB
        return ErrCode.OK.getCode();
    }

    public Student getStudent(String buid, String courseId) {
        Course course = courseService.getCourse(courseId);
        Map<String, Student> students = course.getStudents();
        return students.get(buid);
    }

    public int updateStudent(String firstname, String midname, String lastname, String buid, String comment, String courseId) {
        Course course = courseService.getCourse(courseId);
        Map<String, Student> students = course.getStudents();
        Student student = students.get(buid);
        if (student == null) {
            return ErrCode.STUDENTNOTEXIST.getCode();
        }

        Name name = student.getName();
        name.setFirstName(firstname);
        name.setMiddleName(midname);
        name.setLastName(lastname);
        student.setName(name);
        student.setComment(comment);
        // TODO: update student in DB
        students.put(buid, student);
        course.setStudents(students);
        return ErrCode.OK.getCode();
    }

    public int freezeStudent(String buid, String courseId) {
        Course course = courseService.getCourse(courseId);
        Map<String, Student> students = course.getStudents();
        Student student = students.get(buid);
        if (student == null) {
            return ErrCode.STUDENTNOTEXIST.getCode();
        }

        student.setStatus(Config.FREEZE);
        // TODO: update student in DB
        students.put(buid, student);
        course.setStudents(students);
        return ErrCode.OK.getCode();
    }

    public int deleteStudent(String buid, String courseId) {
        Course course = courseService.getCourse(courseId);
        Map<String, Student> students = course.getStudents();
        if (!students.containsKey(buid)) {
            return ErrCode.STUDENTNOTEXIST.getCode();
        }
        else {
            students.remove(buid);
            // TODO: delete student from DB
            return ErrCode.OK.getCode();
        }
    }
}
