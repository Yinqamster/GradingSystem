package service;

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
//    public StudentService studentService = StudentService.getInstance();

    public static CourseService getInstance() {
        if (instance == null) {
            instance = new CourseService();
        }
        return instance;
    }

    public int addCourse(String name, String section, String semester, String description, String templateName, String filename){
        Course course = new Course(name, section, semester, description);
        if(templateName != null && !templateName.isEmpty()) {
            Breakdown breakdown = templateService.getTemplateMap().get(templateName);
            course.setBreakdown(breakdown);
        }
        if(filename != null && !filename.isEmpty()){
//            course.setStudents(studentService.importStudent(filename));
        }

        //TODO insert course to database

        return ErrCode.OK.getCode();
    }

    public int editCourse(String name, String section, String semester, String description, int courseId) {

        return ErrCode.OK.getCode();
    }

    public static Course getCourse(String courseId) {
        // 此signature加了static(此行可删)
        //TODO get a course by course id
        Course course = new Course();
        return course;
    }

    public static int deleteCourse(String courseId){
        // 此signature加了static(此行可删)
        return ErrCode.OK.getCode();
    }

    public static List<Course> getCourseListBySemester(String semester) {
        // 此signature加了static(此行可删)
        //TODO get course list by semester
        List<Course> courses = new ArrayList<>();

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

    public static String getCourseID(String courseName, String section, List<Course> courseList){
        // TODO get courseID by courseName and section from given courseList
        String courseID = "";
        return courseID;
    }

    public static boolean updateCourse(String courseID, String name, String section, String semester, String description){
        //TODO update the course whose courseID is “String courseID”, return true if update succeeds, else return false
        return true;
    }

    public static Map<String, String> getChooseBreakdownItems(){
        // return items to add into breakdown comboBox, format: Map<breakdownID, name>
        Map<String, String> itemMap = new HashMap(getAllCourseName());
        itemMap.putAll(TemplateService.getAllTemplateName());
        return itemMap;
    }

}
