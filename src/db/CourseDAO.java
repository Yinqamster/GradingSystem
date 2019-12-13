package db;

import model.*;
import utils.ErrCode;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CourseDAO {

    /**
     * CourseID in database is semester + name + section
     */
    public static CourseDAO courseDAO = new CourseDAO();

    private static Connection connection = DBUtil.getInstance();

    private CourseDAO() {
    }

    public static CourseDAO getInstance() {
        return courseDAO;
    }
    public Course getCourse(String courseId) {
        String selectCourseSql = "SELECT semester, name, section FROM course WHERE course_id = ?";
//        Connection conn = DBUtil.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectCourseSql);
            preparedStatement.setObject(1, courseId);
            ResultSet resultSet = preparedStatement.executeQuery();
            String name = "";
            String semester = "";
            String section = "";
            if(resultSet.next()) {
                name = resultSet.getString("name");
                semester = resultSet.getString("semester");
                section = resultSet.getString("section");
            }
            resultSet.close();
            preparedStatement.close();
//            conn.close();
            return getCourse(semester, name, section);
        } catch (SQLException sqle) {
            return new Course();
        }


    }
    public Course getCourse(String semester, String name, String section) {
        try{
            String selectCourseSql = "SELECT * FROM course WHERE semester = ? AND name = ? AND section = ?";
            String selectBreakdownSql = "SELECT break_down_id FROM breakdown WHERE fk_course = ?";
//            Connection conn = DBUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(selectCourseSql);
            preparedStatement.setObject(1, semester);
            preparedStatement.setObject(2, name);
            preparedStatement.setObject(3, section);
            ResultSet resultSet = preparedStatement.executeQuery();
            String description = "";
            String courseId = "";
            Breakdown  breakdown = new Breakdown();
            if (resultSet.next()) {
                description = resultSet.getString("description");
                courseId = resultSet.getString("course_id");
            }
            Map<String, Student> studentMap = new HashMap<>();
            String selectStudentSql = "SELECT student_id from " +
                    "course_student_relationship WHERE course_id = ?";
            preparedStatement = connection.prepareStatement(selectStudentSql);
            preparedStatement.setObject(1, courseId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Student student = StudentDAO.getInstance().getStudent(resultSet.getString(1), courseId);
                studentMap.put(student.getBuid(), student);
            }

            resultSet.close();
            preparedStatement.close();

            preparedStatement = connection.prepareStatement(selectBreakdownSql);
            preparedStatement.setObject(1, courseId);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                String breakDownId = resultSet.getString("break_down_id");
                breakdown = BreakdownDAO.getInstance().getBreakdown(breakDownId);
            }

            Course course = new Course(courseId, name, section, semester, description, studentMap, breakdown);
//            course.setCourseID(courseId);

//            conn.close();
            return course;
        } catch (Exception e) {
            return new Course();
        }
    }

    public int updateCourse(Course course) {
        String name = course.getName();
        String section = course.getSection();
        String semester = course.getSemester();
        String description = course.getDescription();
        String courseid = course.getCourseID();
        String updateSql = "REPLACE INTO course (name, section, semester, description, course_id)" +
                "values (?, ?, ?, ?, ?)";
//        String selectSql = "SELECT * FROM course WHERE name = ? AND section = ? AND semester = ?";
        try {
//            Connection conn = DBUtil.getConnection();
//            PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
//            preparedStatement.setObject(1, name);
//            preparedStatement.setObject(2, section);
//            preparedStatement.setObject(3,semester);
//            preparedStatement.setObject(4, courseid);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            if(resultSet.next()) {
//                return ErrCode.ADDERROR.getCode();
//            }
//            resultSet.close();
//            preparedStatement.close();
            PreparedStatement preparedStatement = connection.prepareStatement(updateSql);
            preparedStatement.setObject(1, name);
            preparedStatement.setObject(2, section);
            preparedStatement.setObject(3, semester);
            preparedStatement.setObject(4, description);
            preparedStatement.setObject(5, courseid);
            int flag = preparedStatement.executeUpdate();
//            conn.close();
            return flag == 0 ? ErrCode.UPDATEERROR.getCode() : ErrCode.OK.getCode();
        } catch (SQLException sqle) {
            return ErrCode.UPDATEERROR.getCode();
        }
    }

    public int deleteCourse(String courseId){
        String deleteSql = "DELETE FROM course WHERE course_id = ?";
        String selectBreakdownSql = "SELECT break_down_id FROM breakdown WHERE fk_course = ?";
        String selectStudentSql = "SELECT student_id FROM course_student_relationship WHERE course_id = ?";
        int deleteFlag = 1;
        List<String> deleteBreakIdList = new ArrayList<>();
        List<String> deleteStudentIdList = new ArrayList<>();
        try {
//            Connection conn = DBUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(selectBreakdownSql);
            preparedStatement.setObject(1, courseId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                String breakdownID = resultSet.getString("break_down_id");
                deleteBreakIdList.add(breakdownID);
//                deleteFlag *= BreakdownDAO.getInstance().deleteBreakdown(breakdownID);
            }
            resultSet.close();
            preparedStatement.close();
            preparedStatement = connection.prepareStatement(selectStudentSql);
            preparedStatement.setObject(1, courseId);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                String buid = resultSet.getString("student_id");
                deleteStudentIdList.add(buid);
            }
            resultSet.close();
            preparedStatement.close();
            preparedStatement = connection.prepareStatement(deleteSql);
            preparedStatement.setObject(1, courseId);
            deleteFlag *= preparedStatement.executeUpdate();
            preparedStatement.close();
//            conn.close();
            for(String str : deleteBreakIdList) {
                deleteFlag *= BreakdownDAO.getInstance().deleteBreakdown(str);
            }
            for(String str : deleteStudentIdList) {
                deleteFlag *= StudentDAO.getInstance().deleteStudent(str, courseId);
            }
            deleteFlag *= GradeDAO.getInstance().deleteGrade(courseId);
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
        String selectSql = "SELECT semester, name, section FROM course";
        try {
//            Connection conn = DBUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                String semester = resultSet.getString("semester");
                String name = resultSet.getString("name");
                String section = resultSet.getString("section");
                result.add(getCourse(semester, name, section));
            }
            resultSet.close();
            preparedStatement.close();
//            conn.close();
            return result;
        } catch (SQLException sqle) {
            return result;
        }
    }

    public String getCourseIdByGradingRuleId(String gradingRuleId) {
        String selectSql = "SELECT fk_breakdown FROM grading_rule WHERE grading_rule_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
            preparedStatement.setObject(1, gradingRuleId);
            ResultSet resultSet = preparedStatement.executeQuery();
            String courseId = "";
            while(resultSet.next()) {
                courseId = resultSet.getString("fk_breakdown");
            }
            return courseId;
        } catch (SQLException sqle) {
            return "";
        }
    }
}
