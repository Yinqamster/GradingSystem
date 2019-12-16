package controller;

import model.Course;
import service.CourseService;

import java.util.List;

public class CourseListController {
    public static void deleteCourse(String courseId){
        CourseService.getInstance().deleteCourse(courseId);
    }

    public static String getCourseID(String courseName, String section, List<Course> courseList){
        return CourseService.getInstance().getCourseID(courseName,section,courseList);
    }
}
