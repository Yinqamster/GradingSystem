package service;

import db.CourseDAO;
import model.Breakdown;
import model.Course;
import model.Template;
import utils.ErrCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseService {

    private static CourseService instance;
    public TemplateService templateService = TemplateService.getInstance();
    public StudentService studentService = StudentService.getInstance();

    public static CourseService getInstance() {
        if (instance == null) {
            instance = new CourseService();
        }
        return instance;
    }

    public static int addCourse(String name, String section, String semester, String description, String templateName, String filename){
        Course course = new Course(name, section, semester, description);
        if(templateName != null && !templateName.isEmpty()) {
            Breakdown breakdown = TemplateService.getTemplateMap().get(templateName);
            course.setBreakdown(breakdown);
        }
        if(filename != null && !filename.isEmpty()){
//            course.setStudents(studentService.importStudent(filename));
        }

        //TODO insert course to database

        return CourseDAO.getInstance().addCourse(course);
    }

    public static int updateCourse(String name, String section, String semester, String description, String courseId) {
        Course course = new Course(name, section, semester, description);
        course.setCourseID(courseId);
        return CourseDAO.getInstance().updateCourse(course);
    }

    public static Course getCourse(String courseId) {
        Course course = CourseDAO.getInstance().getCourse(courseId);
        return course;
    }

    public static int deleteCourse(String courseId){
        return CourseDAO.getInstance().deleteCourse(courseId);
    }

    public static List<Course> getCourseListBySemester(String semester) {
        List<Course> courses = CourseDAO.getInstance().getCourseListBySemester(semester);
        return courses;
    }

    public static List<Course> getAllCourses() {
        //TODO return a list of all courses, including previous courses
        return new ArrayList<Course>();
    }

    public static Map<String,String> getAllCourseName() {
        //TODO return a map of all courses’ names, including previous courses’, format: Map<breakdownID, courseName>
        return new HashMap<>();
    }

    public static String getCourseID(String courseName, String section, List<Course> courseList) {
        // TODO get courseID by courseName and section from given courseList
        String courseID = "";
        return courseID;
    }


}
