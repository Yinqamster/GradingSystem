package service;

import db.CourseDAO;
import db.LetterRuleDAO;
import db.StudentDAO;
import model.*;
import utils.Config;
import utils.ErrCode;

import java.util.*;

public class CourseService {

    private static CourseService instance = new CourseService();
    private CourseService(){

    }
    public static CourseService getInstance() {
//        if (instance == null) {
//            instance = new CourseService();
//        }
        return instance;
    }

    public void generateGradingRuleID(GradingRule gradingRule, String parentId){
        gradingRule.setId(UUID.randomUUID().toString());
        gradingRule.setParentID(parentId);
        if(gradingRule.getChildren() != null && gradingRule.getChildren().size() != 0) {
            for(GradingRule g : gradingRule.getChildren()) {
                generateGradingRuleID(g, gradingRule.getId());
            }
        }
    }

    public int addCourse(String name, String section, String semester, String description, String templateName, String filename, int type) {
        Course course = new Course(name, section, semester, description);
        if (templateName != null && !templateName.isEmpty()) {
            Breakdown breakdown = new Breakdown();
            if (type == Config.TEMPLATE) {
                breakdown = TemplateService.getInstance().getTemplateMap().get(templateName);
                breakdown.setBreakdownID(course.getCourseID());
            } else if (type == Config.BREAKDOWN) {
                breakdown = BreakdownService.getInstance().getBreakdownByID(templateName);
                breakdown.setBreakdownID(course.getCourseID());
            }

            Map<String, GradingRule> gradingRuleMap = breakdown.getGradingRules();
            Map<String, GradingRule> newGradingRuleMap = new HashMap<>();
            for(GradingRule gradingRule : gradingRuleMap.values()) {
                generateGradingRuleID(gradingRule, "");
//                gradingRule.setId(UUID.randomUUID().toString());
//                while(gradingRule.getChildren() != null || gradingRule.getChildren().size() != 0) {
//
//                }
                newGradingRuleMap.put(gradingRule.getId(), gradingRule);
            }
            breakdown.setGradingRules(newGradingRuleMap);

            course.setBreakdown(breakdown);
        }


        //add student to db
        int res = CourseDAO.getInstance().addCourse(course);
        if(res == ErrCode.OK.getCode()) {
            CourseDAO courseDAO = CourseDAO.getInstance();
            String courseId = CourseDAO.getInstance().getCourse(semester, name, section).getCourseID();
            course.setCourseID(courseId);
            if(filename != null && !filename.isEmpty()){
                course.setStudents(StudentService.getInstance().importStudent(filename));
                for(Student s : course.getStudents().values()) {
                    int resStu = StudentDAO.getInstance().addStudent(courseId, s);
                    if(resStu != ErrCode.OK.getCode()) {
                        return resStu;
                    }
                }
            }
        }
        else {
            System.out.println(res);
            return res;
        }

        //add letter rule
        System.out.println("add letter rule");
        res = LetterRuleDAO.getInstance().updateBreakdownLetterMap(course.getBreakdown().getLetterRule(), course.getCourseID());
        if(res != ErrCode.OK.getCode()) {
            return res;
        }
        return ErrCode.OK.getCode();
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
        List<Course> courses = CourseDAO.getInstance().getAllCourses();
        if(courses == null || courses.isEmpty()) return null;
        //breakdownID, courseName
        Map<String, String> coursesNames = new HashMap<>();
        for(Course course : courses) {
            coursesNames.put(course.getBreakdown().getBreakdownID(), course.getName());
        }
        return coursesNames;
    }

    public String getCourseID(String courseName, String section, List<Course> courseList) {
        //get courseID by courseName and section from given courseList
        String courseID = "";
        for(Course course : courseList) {
            if(course.getName().equals(courseName) && course.getSection().equals(section)) {
                courseID = course.getCourseID();
            }
        }
        return courseID;
    }

}
