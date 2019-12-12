package db;

import model.*;
import utils.ErrCode;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentDAO {

    // Student has a Category in database, 0 for undergraduate, 1 for graduate

    public static StudentDAO studentDAO = new StudentDAO();

    private StudentDAO() {
    }

    public static StudentDAO getInstance() {
        return studentDAO;
    }

    public Student getStudent(String buid, String courseId) {
        try {
            String selectSql = "SELECT * FROM student WHERE buid = ?";
            Connection conn = DBUtil.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(selectSql);
            Name name = NameDAO.getInstance().getName(buid);
            preparedStatement.setObject(1, buid);
            ResultSet resultSet = preparedStatement.executeQuery();

            int status = 0;
            double bonus = 0;
            String comment = "";
            Map<String, Grade> gradeList = new HashMap<>();
            int category = 0;

            if (resultSet.next()) {
                status = Integer.parseInt(resultSet.getString("status"));
                bonus = Double.parseDouble(resultSet.getString("bonus"));
                comment = resultSet.getString("comment");
                gradeList = GradeDAO.getInstance().getGradeList(buid, courseId);
                category = resultSet.getInt("category");
            }
            resultSet.close();
            preparedStatement.close();
            conn.close();
            if (category == 1) {
                return new GraduateStudent(name, buid, status, bonus, gradeList, comment);
            } else if (category == 0) {
                return new UndergraduateStudent(name, buid, status, bonus, comment, gradeList);
            } else {
                System.err.println("The student does not have any category");
                return new GraduateStudent();
            }
        } catch (SQLException sqle) {
            return new GraduateStudent();
        }
    }

    public int updateStudent(Student student) {
        try {
            Connection conn = DBUtil.getConnection();
            String updateSql = "REPLACE INTO student (buid, first_name, middle_name, " +
                    "last_name, status, comment, bonus, category) values (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(updateSql);
            preparedStatement.setObject(1, student.getBuid());
            preparedStatement.setObject(2, student.getName().getFirstName());
            preparedStatement.setObject(3, student.getName().getMiddleName());
            preparedStatement.setObject(4, student.getName().getLastName());
            preparedStatement.setObject(5, student.getStatus());
            preparedStatement.setObject(6, student.getComment());
            preparedStatement.setObject(7, student.getBonus());
            if (student instanceof GraduateStudent) {
                preparedStatement.setObject(8, 1);
            } else if (student instanceof UndergraduateStudent) {
                preparedStatement.setObject(8, 0);
            } else {
                return ErrCode.STUDENTTYPEERROR.getCode();
            }
            int returnValue = preparedStatement.executeUpdate();
            preparedStatement.close();
            conn.close();
            if (returnValue == 0) {
                return ErrCode.STUDENTNOTEXIST.getCode();
            } else {
                return ErrCode.OK.getCode();
            }
        } catch (SQLException sqle) {
            System.out.println(sqle.getSQLState());
            return ErrCode.UPDATEERROR.getCode();
        }
    }

    public int updateStudent(String firstname, String midname, String lastname,
                             String buid, String comment, String courseId) {
        try {
            String updateSql = "REPLACE INTO student (buid, first_name, middle_name, last_name, comment) " +
                    "values (?, ?, ?, ?, ?)";
            Connection conn = DBUtil.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(updateSql);
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
            conn.close();
            if (returnValue == 0) {
                return ErrCode.STUDENTNOTEXIST.getCode();
            }
            return ErrCode.OK.getCode();
        } catch(SQLException sqle) {
            return ErrCode.UPDATEERROR.getCode();
        }
    }

    public int addStudent(String firstname, String midname, String lastname, String buid,
                          String year, String comment, String courseId) {
        this.updateStudent(firstname, midname, lastname, buid, comment, courseId);
        try {
            String updateSql = "UPDATE student SET category  = ? WHERE buid = ?";
            Connection conn = DBUtil.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(updateSql);
            if (year.equalsIgnoreCase("undergraduate")) {
                preparedStatement.setObject(1, 0);
            } else if (year.equalsIgnoreCase("graduate")) {
                preparedStatement.setObject(1, 1);
            } else {
                return ErrCode.STUDENTTYPEERROR.getCode();
            }
            preparedStatement.setObject(2, buid);
            preparedStatement.close();
//            String update
            conn.close();
            return ErrCode.OK.getCode();
        } catch(SQLException sqle) {
            return ErrCode.UPDATEERROR.getCode();
        }
    }

    //TODO
    public int addStudent(String courseId, Student student){
        int updateFlag;
        updateFlag = updateStudent(student);
        String updateSql = "INSERT OR REPLACE INTO course_student_relationship (course_id, student_id) " +
                "values (?, ?)";
        String updateGradeSql = "REPLACE INTO grade (fk_student, fk_grading_rule, absolute_score, percentage_score," +
                " comment, fk_course, name) values (?, ?, ?, ?, ?, ?, ?)";
        try {
            Connection conn = DBUtil.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(updateSql);
            preparedStatement.setObject(1, courseId);
            preparedStatement.setObject(2, student.getBuid());
            updateFlag *= preparedStatement.executeUpdate();
            preparedStatement.close();
            List<Grade> gradeList = getGradeList(courseId);
            GradeDAO.getInstance().updateGradeList(student.getBuid(), courseId, gradeList);
            List<List<Object>> gradingRuleList = GradeDAO.getInstance().getGradeFromGradingRule(courseId);
            for(List<Object> tempList : gradingRuleList) {
                String name = tempList.get(0).toString();
                String gradingRuleId = tempList.get(1).toString();
                double fullScore = Double.parseDouble(tempList.get(2).toString());
                preparedStatement = conn.prepareStatement(updateGradeSql);
                preparedStatement.setObject(1, student.getBuid());
                preparedStatement.setObject(2, gradingRuleId);
                preparedStatement.setObject(3, fullScore);
                preparedStatement.setObject(4, 1);
                preparedStatement.setObject(5, "");
                preparedStatement.setObject(6, courseId);
                preparedStatement.setObject(7, name);
                updateFlag *= preparedStatement.executeUpdate();
            }
            conn.close();
            return updateFlag == 0 ? ErrCode.UPDATEERROR.getCode() : ErrCode.OK.getCode();
        } catch(SQLException sqle) {
            return ErrCode.UPDATEERROR.getCode();
        }
    }

    public int addStudent(String courseId, String studentId){
        int updateFlag = 1;
        String updateSql = "REPLACE INTO course_student_relationship (course_id, student_id) " +
                "values (?, ?)";
        try {
            Connection conn = DBUtil.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(updateSql);
            preparedStatement.setObject(1, courseId);
            preparedStatement.setObject(2, studentId);
            updateFlag *= preparedStatement.executeUpdate();
            preparedStatement.close();
            conn.close();
            return updateFlag == 0 ? ErrCode.UPDATEERROR.getCode() : ErrCode.OK.getCode();
        } catch(SQLException sqle) {
            return ErrCode.UPDATEERROR.getCode();
        }
    }


    public Student freezeStudent(String buid, String courseId) {
        try {
            String updateSql = "UPDATE student SET status = ? WHERE buid = ?";
            PreparedStatement preparedStatement = DBUtil.getConnection().prepareStatement(updateSql);
            preparedStatement.setObject(1, 0);
            preparedStatement.setObject(2, buid);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            DBUtil.getConnection().close();
            return this.getStudent(buid, courseId);
        } catch (SQLException sqle) {
            return new GraduateStudent();
        }
    }

    public int deleteStudent(String buid, String courseId) {
        try {
            String deleteStudentSql = "DELETE FROM student WHERE buid = ?";
            PreparedStatement preparedStatement = DBUtil.getConnection().prepareStatement(deleteStudentSql);
            preparedStatement.setObject(1, buid);
            int deleteReturnValue = preparedStatement.executeUpdate();
            if (deleteReturnValue == 0) {
                return ErrCode.STUDENTNOTEXIST.getCode();
            }
            preparedStatement.close();
            String deleteCourseSql = "DELETE FROM course_student_relationship WHERE course_id = ? AND student_id = ?";
            preparedStatement = DBUtil.getConnection().prepareStatement(deleteCourseSql);
            preparedStatement.setObject(1, courseId);
            preparedStatement.setObject(2, buid);
            deleteReturnValue = preparedStatement.executeUpdate();
            deleteReturnValue *= GradeDAO.getInstance().deleteGradeList(buid, courseId);
            if (deleteReturnValue == 0) {
                return ErrCode.COURSENOTEXIST.getCode();
            }
            return ErrCode.OK.getCode();
        } catch (SQLException sqle) {
            return ErrCode.UPDATEERROR.getCode();
        }
    }

    public List<Grade> getGradeList(String courseId) {
        String selectSql = "SELECT * FROM grading_rule WHERE fk_breakdown = ?";
        List<Grade> result = new ArrayList<>();
        try {
            Connection conn = DBUtil.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(selectSql);
            preparedStatement.setObject(1, courseId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                String name = resultSet.getString("name");
                double fullScore = resultSet.getDouble("full_score");
                String ruleId = resultSet.getString("grading_rule_id");
                result.add(new Grade(ruleId, fullScore, 1, 0, ""));
            }
            resultSet.close();
            preparedStatement.close();
            conn.close();
            return result;
        } catch(SQLException sqle) {
            return result;
        }
    }

    public List<String> getStudentIdByCourseId(String courseId) {
        List<String> result = new ArrayList<>();
        String selectSql = "SELECT student_id FROM course_student_relationship WHERE course_id = ?";
        try {
            Connection conn = DBUtil.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(selectSql);
            preparedStatement.setObject(1, courseId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                result.add((resultSet.getString(1)));
            }
            resultSet.close();
            preparedStatement.close();
            conn.close();
            return result;
        } catch(SQLException sqle) {
            return result;
        }
    }
}
