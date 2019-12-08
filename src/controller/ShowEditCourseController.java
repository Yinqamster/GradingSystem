package controller;

import model.Course;
import service.CourseService;

public class ShowEditCourseController {
    public static Course getCourseByID(String courseID){
        return CourseService.getInstance().getCourse(courseID);
    }

    public static int updateCourse(String name, String section, String semester, String description, String courseId){
        return CourseService.getInstance().updateCourse(name,section,semester,description,courseId);
    }
}
