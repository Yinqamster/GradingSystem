package db;

import model.*;
import utils.ErrCode;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StudentDAO{

    // Student has a Category in database, 0 for undergraduate, 1 for graduate

    public static StudentDAO studentDAO = new StudentDAO();

    private StudentDAO() {
    }

    public static StudentDAO getInstance() {
        return studentDAO;
    }

    public Student getStudent(String buid) throws SQLException {
        String selectSql = "SELECT * FROM student WHERE buid = ?";
        PreparedStatement preparedStatement = DBUtil.getConnection().prepareStatement(selectSql);
        Name name = NameDAO.getInstance().getName(buid);
        preparedStatement.setObject(1, buid);
        ResultSet resultSet = preparedStatement.executeQuery();

        int status = 0;
        double bonus = 0;
        String comment = "";
        Map<String, Grade> gradeList = null;
        int category = 0;

        if(resultSet.next()) {
            status = Integer.parseInt(resultSet.getString("status"));
            bonus = Double.parseDouble(resultSet.getString("bonus"));
            comment = resultSet.getString("comment");
            gradeList = GradeDAO.getInstance().getGradeList(buid);
            category = resultSet.getInt("category");
        }
        resultSet.close();
        preparedStatement.close();
        DBUtil.getConnection().close();
        if(category == 1) {
            return new GraduateStudent(name, buid, status, bonus, gradeList, comment);
        }
        else if(category == 0) {
            return new UndergraduateStudent(name, buid, status, bonus, comment, gradeList);
        }
        else {
            System.err.println("The student does not have any category");
            return null;
        }
    }

    public Student getStudent(String buid, String courseId) throws SQLException {
        return this.getStudent(buid);
    }

    public int updateStudent(Student student) throws SQLException{
        String updateSql = "REPLACE INTO student (buid, first_name, middle_name, " +
                    "last_name, status, comment, bonus, category) values (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = DBUtil.getConnection().prepareStatement(updateSql);
        preparedStatement.setObject(1, student.getBuid());
        preparedStatement.setObject(2, student.getName().getFirstName());
        preparedStatement.setObject(3, student.getName().getMiddleName());
        preparedStatement.setObject(4, student.getName().getLastName());
        preparedStatement.setObject(5, student.getStatus());
        preparedStatement.setObject(6, student.getComment());
        preparedStatement.setObject(7, student.getBonus());
        if(student instanceof GraduateStudent) {
            preparedStatement.setObject(8, 1);
        }
        else if(student instanceof UndergraduateStudent) {
            preparedStatement.setObject(8, 0);
        }
        else {
            return ErrCode.STUDENTTYPEERROR.getCode();
        }
        int returnValue = preparedStatement.executeUpdate();
        if(returnValue == 0) {
            return ErrCode.STUDENTNOTEXIST.getCode();
        }
        else {
            return ErrCode.OK.getCode();
        }
    }

    public int updateStudent(String firstname, String midname, String lastname,
                             String buid, String comment, String courseId) throws SQLException {
//        String updateSql = "UPDATE student SET first_name = ?, middle_name = ?, last_name = ?," +
//                "comment = ? WHERE buid = ?";
        String updateSql = "REPLACE INTO student (buid, first_name, middle_name, last_name, comment) " +
                "values (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = DBUtil.getConnection().prepareStatement(updateSql);
        preparedStatement.setObject(1, buid);
        preparedStatement.setObject(2, firstname);
        preparedStatement.setObject(3, midname);
        preparedStatement.setObject(4, lastname);
        preparedStatement.setObject(5, comment);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        String updateCourseStudentTableRelationship = "REPLACE INTO course_student_relationship" +
                "(course_id, student_id) values (?, ?)";
        preparedStatement = DBUtil.getConnection().prepareStatement(updateCourseStudentTableRelationship);
        preparedStatement.setObject(1, courseId);
        preparedStatement.setObject(2, buid);
        int returnValue = preparedStatement.executeUpdate();
        preparedStatement.close();
        DBUtil.getConnection().close();
        if(returnValue == 0) {
            return ErrCode.STUDENTNOTEXIST.getCode();
        }
        return ErrCode.OK.getCode();
    }

    public int addStudent(String firstname, String midname, String lastname, String buid,
                          String year, String comment, String courseId) throws SQLException {
        this.updateStudent(firstname, midname, lastname, buid, comment, courseId);
        String updateSql = "UPDATE student SET category  = ? WHERE buid = ?";
        PreparedStatement preparedStatement = DBUtil.getConnection().prepareStatement(updateSql);
        if(year.equalsIgnoreCase("undergraduate")) {
            preparedStatement.setObject(1, 0);
        }
        else if(year.equalsIgnoreCase("graduate")) {
            preparedStatement.setObject(1, 1);
        }
        else {
            return ErrCode.STUDENTTYPEERROR.getCode();
        }
        preparedStatement.setObject(2, buid);
        return ErrCode.OK.getCode();
    }

    public Student freezeStudent(String buid, String courseId) throws SQLException {
        String updateSql = "UPDATE student SET status = ? WHERE buid = ?";
        PreparedStatement preparedStatement = DBUtil.getConnection().prepareStatement(updateSql);
        preparedStatement.setObject(1, 0);
        preparedStatement.setObject(2, buid);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        DBUtil.getConnection().close();
        return this.getStudent(buid);
    }

    public int deleteStudent(String buid, String courseId) throws SQLException {
        String deleteStudentSql = "DELETE FROM student WHERE buid = ?";
        PreparedStatement preparedStatement = DBUtil.getConnection().prepareStatement(deleteStudentSql);
        preparedStatement.setObject(1, buid);
        int deleteReturnValue = preparedStatement.executeUpdate();
        if(deleteReturnValue == 0) {
            return ErrCode.STUDENTNOTEXIST.getCode();
        }
        preparedStatement.close();
        String deleteCourseSql = "DELETE FROM course_student_relationship WHERE course_id = ? AND student_id = ?";
        preparedStatement = DBUtil.getConnection().prepareStatement(deleteCourseSql);
        preparedStatement.setObject(1, courseId);
        preparedStatement.setObject(2, buid);
        deleteReturnValue = preparedStatement.executeUpdate();
        if(deleteReturnValue == 0) {
            return ErrCode.COURSENOTEXIST.getCode();
        }
        return ErrCode.OK.getCode();
    }
}