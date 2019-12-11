package service;

import model.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import utils.Config;
import utils.ErrCode;
import db.StudentDAO;
import db.CourseDAO;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class StudentService {
    private static StudentService instance = new StudentService();

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
        // read students from worksheet
        /*the column of the first five cols should be
         * student type   Undergraduate/Graduate
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
//            System.out.println(rolNum + " " + colNum);

            for(int i = 1; i <= rolNum; i++) {
                Row row = sheet.getRow(i);
                List<Object> elements = new ArrayList<>();
                for(int j = 0; j < colNum; j++) {
                    Cell cell = row.getCell(j);

                    if(cell == null) {
//                        System.out.println();
                        elements.add("");
                    }
                    else {
                        if(cell.getCellTypeEnum() == CellType.NUMERIC) {
//                            System.out.println(cell.getNumericCellValue());
                            elements.add(cell.getNumericCellValue());
                        }
                        else if(cell.getCellTypeEnum() == CellType.STRING) {
//                            System.out.println(cell.getStringCellValue());
                            elements.add(cell.getStringCellValue());
                        }
//                        System.out.println(cell.getStringCellValue());

                    }
                }
                Name name = new Name(elements.get(2).toString(), elements.get(3).toString(), elements.get(4).toString());
                Student student = elements.get(0).toString().equals(Config.UNDERGRADUATE) ? new UndergraduateStudent(name, elements.get(1).toString())
                        : new GraduateStudent(name, elements.get(1).toString());
//                System.out.println(student.getBuid() + " " + student.getName().getFullName());
                students.put(student.getBuid(), student);
            }

            //only for test, can be deleted
            //printStudent(students);
        } catch(Exception e) {
            return null;
        }
        return students;
    }

    //only for test
    public void printStudent(Map<String, Student> students) {
        System.out.println("student size: " + students.size());
        for(Student s : students.values()) {
            System.out.print(s instanceof UndergraduateStudent ? Config.UNDERGRADUATE : Config.GRADUATE);
            System.out.print(" " + s.getBuid());
            System.out.println(" " + s.getName().getFullName());
        }
    }

    public int addStudent(String firstname, String midname, String lastname, String buid, String year, String comment, String courseId) {
        Name name = new Name(firstname, midname, lastname);
        Student student;
        if (year.equalsIgnoreCase("undergraduate")) {
            student = new UndergraduateStudent(name, buid, comment);
        }
        else {
            student = new GraduateStudent(name, buid, comment);
        }

        System.out.println(CourseService.getInstance() == null);
        System.out.println(CourseService.getInstance() == null);
        Course course = CourseService.getInstance().getCourse(courseId);
        System.out.println(course == null);
        Map<String, Student> students = course.getStudents();
        System.out.println(students == null);
        if (students.containsKey(buid)) {
            return ErrCode.STUDENTEXIST.getCode();
        }
        students.put(buid, student);
        // add student to DB
        StudentDAO.getInstance().addStudent(firstname, midname, lastname, buid, year, comment, courseId);

        course.setStudents(students);
        //update course in DB
        return CourseDAO.getInstance().updateCourse(course);
    }

    public Student getStudent(String buid, String courseId) {
        Course course = CourseService.getInstance().getCourse(courseId);
        Map<String, Student> students = course.getStudents();
        return students.get(buid);
    }

    public int updateStudent(String firstname, String midname, String lastname, String buid, String comment, String courseId) {
        Course course = CourseService.getInstance().getCourse(courseId);
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
        // update student in DB
        return StudentDAO.getInstance().updateStudent(student);
    }

    public int freezeStudent(String buid, String courseId) {
        Course course = CourseService.getInstance().getCourse(courseId);
        Map<String, Student> students = course.getStudents();
        Student student = students.get(buid);
        if (student == null) {
            return ErrCode.STUDENTNOTEXIST.getCode();
        }

        student.setStatus(Config.FREEZE);
        // freeze student in DB
        StudentDAO.getInstance().freezeStudent(buid, courseId);

        return ErrCode.OK.getCode();
    }

    public int deleteStudent(String buid, String courseId) {
        Course course = CourseService.getInstance().getCourse(courseId);
        Map<String, Student> students = course.getStudents();
        if (!students.containsKey(buid)) {
            return ErrCode.STUDENTNOTEXIST.getCode();
        }
        else {
            students.remove(buid);
            // delete student from DB
            return StudentDAO.getInstance().deleteStudent(buid, courseId);
        }
    }
}
