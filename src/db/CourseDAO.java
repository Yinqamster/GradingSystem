package db;

import model.*;
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
    public Course getCourse(String semester, String name, String section) {
        String courseId = semester + name + section;
        return getCourse(courseId);
    }
    public Course getCourse(String courseId) {
        try{
            String selectCourseSql = "SELECT * FROM course WHERE course_id = ?";
            PreparedStatement preparedStatement = DBUtil.getConnection().prepareStatement(selectCourseSql);
            preparedStatement.setObject(1, courseId);
            ResultSet resultSet = preparedStatement.executeQuery();
            String description = "";
            String name = "";
            String section = "";
            String semester = "";
            if (resultSet.next()) {
                description = resultSet.getString("description");
                name = resultSet.getString("name");
                section = resultSet.getString("section");
                semester = resultSet.getString("semester");
            }
            resultSet.close();
            preparedStatement.close();
            DBUtil.getConnection().close();
            Map<String, Student> studentMap = new HashMap<>();
            String selectStudentSql = "SELECT student_id from " +
                    "course_student_relationship WHERE course_id = ?";
            preparedStatement = DBUtil.getConnection().prepareStatement(selectStudentSql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Student student = StudentDAO.studentDAO.getStudent(resultSet.getString(1), courseId);
                studentMap.put(student.getBuid(), student);
            }
            return new Course(name, section, semester, description, studentMap);
        } catch (Exception e) {
            return null;
        }
    }

    public int updateCourse(Course course) {
        String name = course.getName();
        String section = course.getSection();
        String semester = course.getSemester();
        String description = course.getDescription();
        String courseid = semester + name + section;
        String updateSql = "REPLACE INTO course (course_id, name, section, semester, description)" +
                "values (?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = DBUtil.getConnection().prepareStatement(updateSql);
            preparedStatement.setObject(1, courseid);
            preparedStatement.setObject(2, name);
            preparedStatement.setObject(3, section);
            preparedStatement.setObject(4, semester);
            preparedStatement.setObject(5, description);
            int flag = preparedStatement.executeUpdate();
            return flag == 0 ? ErrCode.UPDATEERROR.getCode() : ErrCode.OK.getCode();
        } catch (SQLException sqle) {
            return ErrCode.UPDATEERROR.getCode();
        }
    }

    public int deleteCourse(String courseId){
        String deleteSql = "DELETE FROM course WHERE course_id = ?";
        String selectSql = "SELECT break_down_id FROM breakdown WHERE fk_course = ?";
        int deleteFlag = 1;
        try {
            PreparedStatement preparedStatement = DBUtil.getConnection().prepareStatement(selectSql);
            preparedStatement.setObject(1, courseId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                String breakdownID = resultSet.getString("break_down_id");
                deleteFlag *= BreakdownDAO.getInstance().deleteBreakdown(breakdownID);
            }
            resultSet.close();
            preparedStatement.close();
            DBUtil.getConnection().close();
            preparedStatement = DBUtil.getConnection().prepareStatement(deleteSql);
            preparedStatement.setObject(1, courseId);
            deleteFlag *= preparedStatement.executeUpdate();
        } catch (SQLException sqle) {
            return ErrCode.DELETEERROR.getCode();
        }
        return deleteFlag == 0 ? ErrCode.DELETEERROR.getCode() : ErrCode.OK.getCode();
    }

    public int addCourse(Course course) {
        return updateCourse(course);
    }

    public List<Course> getAllCourses() {
        List<Course> result = new ArrayList<>();
        String selectSql = "SELECT course_id FROM course";
        try {
            PreparedStatement preparedStatement = DBUtil.getConnection().prepareStatement(selectSql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                String courseId = resultSet.getString("course_id");
                result.add(getCourse(courseId));
            }
            resultSet.close();
            preparedStatement.close();
            DBUtil.getConnection().close();
            return result;
        } catch (SQLException sqle) {
            return null;
        }
    }

    public List<Course> getCourseListBySemester(String semester) {
        String selectSql = "SELECT course_id FROM course WHERE semester = ?";
        List<Course> result = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = DBUtil.getConnection().prepareStatement(selectSql);
            preparedStatement.setObject(1, semester);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                String courseId = resultSet.getString("course_id");
                result.add(getCourse(courseId));
            }
            resultSet.close();
            preparedStatement.close();
            DBUtil.getConnection().close();
            return result;
        } catch (SQLException sqle) {
            return null;
        }
    }

}
