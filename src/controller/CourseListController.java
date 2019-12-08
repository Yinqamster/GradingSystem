package controller;

import model.Course;
import service.CourseService;
import service.TemplateService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseListController {
    public static void deleteCourse(String courseId){
        CourseService.getInstance().deleteCourse(courseId);
    }

    public static String getCourseID(String courseName, String section, List<Course> courseList){
        return CourseService.getInstance().getCourseID(courseName,section,courseList);
    }
}
