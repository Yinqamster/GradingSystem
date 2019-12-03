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

public class CourseDAO {

    /**
     * CourseID in database is semester + name + section
     */
    public static CourseDAO courseDAO = new CourseDAO();

    private CourseDAO() {
    }

    public static CourseDAO getInstance() {
        return courseDAO;
    }
    public Course getCourse(String semester, String name, String section) throws SQLException {
        String courseId = semester + name + section;
        String selectCourseSql = "SELECT * FROM course WHERE course_id = ?";
        PreparedStatement preparedStatement = DBUtil.getConnection().prepareStatement(selectCourseSql);
        preparedStatement.setObject(1, courseId);
        ResultSet resultSet = preparedStatement.executeQuery();
        String description = "";
        if (resultSet.next()) {
            description = resultSet.getString("description");
        }

        Map<String, Student> studentMap = new HashMap<>();
        String selectStudentSql = "SELECT student_id from " +
                "course_student_relationship WHERE course_id = ?";
        preparedStatement = DBUtil.getConnection().prepareStatement(selectStudentSql);
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Student student = StudentDAO.studentDAO.getStudent(resultSet.getString(1));
            studentMap.put(student.getBuid(), student);
        }
        return null;
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

    public int addCourse(String name, String section, String semester,
                         String description, String templateName, String filename) {
        return 0;
    }

    public int addCourse(String name, String section, String semester,
                         String description) throws SQLException {
        return this.editCourse(name, section, semester, description);
    }

    public int editCourse(String name, String section,
                          String semester, String description) throws SQLException {
        String courseId = semester + name + section;
        String updateSql = "REPLACE INTO course (course_id, name, section, semester, description)" +
                "values (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = DBUtil.getConnection().prepareStatement(updateSql);
        preparedStatement.setObject(1, courseId);
        preparedStatement.setObject(2, name);
        preparedStatement.setObject(3, section);
        preparedStatement.setObject(4, semester);
        preparedStatement.setObject(5, description);
        int returnValue = preparedStatement.executeUpdate();
        preparedStatement.close();
        DBUtil.getConnection().close();
        if(returnValue == 0) {
            return ErrCode.COURSENOTEXIST.getCode();
        }
        else {
            return ErrCode.OK.getCode();
        }
    }
    public List<Course> getCourseListBySemester(String semester) {
        //TODO get course list by semester
        List<Course> courses = new ArrayList<>();

        return courses;
    }

    public List<Course> getAllCourses() {
        //TODO return a list of all courses, including previous courses
        return new ArrayList<Course>();
    }

    public Map<String,String> getAllCourseName() {
        //TODO return a map of all courses’ names, including previous courses’, format: Map<breakdownID, courseName>
        return new HashMap<>();
    }

//    public String getCourseID(String courseName, String section, List<Course> courseList){
//        // TODO get courseID by courseName and section from given courseList
//        String courseID = "";
//        return courseID;
//    }

    public int updateCourse(Course course){
        //TODO update the course whose courseID is “String courseID”
        return ErrCode.OK.getCode();
    }

//    public int updateStudent(Student student, String courseID) throws SQLException{
//        return 0;
//    }
}
