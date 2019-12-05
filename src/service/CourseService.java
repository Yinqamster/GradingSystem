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

    private static CourseService instance = new CourseService();
    public TemplateService templateService = TemplateService.getInstance();
    public StudentService studentService = StudentService.getInstance();

    private CourseService(){

    }
    public static CourseService getInstance() {
//        if (instance == null) {
//            instance = new CourseService();
//        }
        return instance;
    }

    public int addCourse(String name, String section, String semester, String description, String templateName, String filename){
        Course course = new Course(name, section, semester, description);
        if(templateName != null && !templateName.isEmpty()) {
            Breakdown breakdown = TemplateService.getInstance().getTemplateMap().get(templateName);
            course.setBreakdown(breakdown);
        }
        if(filename != null && !filename.isEmpty()){
//            course.setStudents(studentService.importStudent(filename));
        }

        return CourseDAO.getInstance().addCourse(course);
    }

    public int updateCourse(String name, String section, String semester, String description, String courseId) {
        Course course = new Course(name, section, semester, description);
        course.setCourseID(courseId);
        return CourseDAO.getInstance().updateCourse(course);
    }

    public Course getCourse(String courseId) {
        Course course = CourseDAO.getInstance().getCourse(courseId);
        return course;
    }

    public int deleteCourse(String courseId){
        return CourseDAO.getInstance().deleteCourse(courseId);
    }

    public List<Course> getCourseListBySemester(String semester) {
        List<Course> courses = CourseDAO.getInstance().getAllCourses();
        if(courses == null || courses.isEmpty()) return new ArrayList<Course>();
        List<Course> coursesSemester = new ArrayList<>();
        for(Course course : courses) {
            if(course.getSemester().equals(semester)) {
                coursesSemester.add(course);
            }
        }
        return coursesSemester;
    }

    public List<Course> getAllCourses() {
        return CourseDAO.getInstance().getAllCourses();
    }

    public Map<String,String> getAllCourseName() {
        //TODO return a map of all courses’ names, including previous courses’, format: Map<breakdownID, courseName>
        List<Course> courses = CourseDAO.getInstance().getAllCourses();
        if(courses == null || courses.isEmpty()) return null;
        Map<String, String> coursesNames = new HashMap<>();
        for(Course course : courses) {
            coursesNames.put(course.getBreakdown().getBreakdownID(), course.getName());
        }
        return coursesNames;
    }

    public String getCourseID(String courseName, String section, List<Course> courseList) {
        // TODO get courseID by courseName and section from given courseList
        String courseID = "";
        return courseID;
    }

}
