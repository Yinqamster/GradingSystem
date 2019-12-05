package service;

import model.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import utils.Config;
import utils.ErrCode;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

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
        /*the column of the first four cols should be
         * buid
         * first name
         * middle name
         * last name
         * and the cols after these cols don't matter.
         */
        try {
            File file = new File(filename);   //creating a new file instance
            FileInputStream fis = new FileInputStream(file);   //obtaining bytes from the file
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet sheet = wb.getSheetAt(0);     //creating a Sheet object to retrieve object

            int rolNum = sheet.getLastRowNum();
            int colNum = sheet.getRow(0).getLastCellNum();

            for(int i = 1; i <= rolNum; i++) {
                Row row = sheet.getRow(i);
                List<Object> elements = new ArrayList<>();
                for(int j = 0; j < colNum; j++) {
                    Cell cell = row.getCell(j);
                    if(cell == null) {
                        elements.add("");
                    }
                    else {
                        elements.add(cell.getStringCellValue());
                    }
                }
                Name name = new Name(elements.get(1).toString(), elements.get(2).toString(), elements.get(3).toString());
                Student student = new UndergraduateStudent(name, elements.get(0).toString());
                students.put(student.getBuid(), student);
            }

            //only for test, can be deleted
            printStudent(students);
        } catch(Exception e) {
            return null;
        }
        // TODO: add students to DB
        return students;
    }

    public void printStudent(Map<String, Student> students) {
        System.out.println("student size: " + students.size());
        for(Student s : students.values()) {
            System.out.print(s.getBuid());
            System.out.println(" " + s.getName().getFullName());
        }
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
