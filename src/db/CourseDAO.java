package db;

import model.*;
import service.TemplateService;
import utils.ErrCode;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseDAO extends DAOImpl{

    public static CourseDAO courseDAO = new CourseDAO();

    private CourseDAO() {

    }

    public static CourseDAO getInstance() {
        return courseDAO;
    }

    public Course getCourse(String courseID) {
        try{
            String selectSql = "SELECT * FROM course WHERE course_id = ?";
            PreparedStatement preparedStatement = DBUtil.getConnection().prepareStatement(selectSql);

            return null;
        } catch (Exception e) {
            return null;
        }

    }

    public int addCourse(Course course) {
        //TODO insert a course into database
        return ErrCode.OK.getCode();
    }

    public int deleteCourse(String courseId){
        //TODO delete a course
        return ErrCode.OK.getCode();
    }

    public List<Course> getAllCourses() {
        //TODO return a list of all courses, including previous courses
        return new ArrayList<Course>();
    }


    public int updateCourse(Course course){
        //TODO update the course whose courseID is “String courseID”
        return ErrCode.OK.getCode();
    }

//    public int updateStudent(Student student, String courseID) throws SQLException{
//        return 0;
//    }
}
