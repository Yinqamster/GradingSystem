package service;

import model.Breakdown;
import model.Course;
import utils.ErrCode;

import java.util.ArrayList;
import java.util.List;

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

    public int addCourse(String name, String section, String semester, String description, String templateName, String filename){
        Course course = new Course(name, section, semester, description);
        if(templateName != null && !templateName.isEmpty()) {
            Breakdown breakdown = templateService.getTemplateMap().get(templateName);
            course.setBreakdown(breakdown);
        }
        if(filename != null && !filename.isEmpty()){
            course.setStudents(studentService.importStudent(filename));
        }

        //TODO insert course to database

        return ErrCode.OK.getCode();
    }

    public int editCourse(String name, String section, String semester, String description, int courseId) {

        return ErrCode.OK.getCode();
    }

    public Course getCourse(int courseId) {
        //TODO get a course by course id
        Course course = new Course();
        return course;
    }

    public int deleteCourse(int courseId){

        return ErrCode.OK.getCode();
    }

    public List<Course> getCourseListBySemester(String semester) {
        //TODO get course list by semester
        List<Course> courses = new ArrayList<>();

        return courses;
    }
}
