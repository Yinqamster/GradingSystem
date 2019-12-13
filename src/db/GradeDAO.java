package db;

import model.*;
import utils.ErrCode;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GradeDAO {

    public static GradeDAO gradeDAO = new GradeDAO();

    private GradeDAO() {
    }

    public static GradeDAO getInstance() {
        return gradeDAO;
    }

    private static Connection connection = DBUtil.getInstance();

    public int updateFinalGrade(String buid, String courseid, double absoluate_score, double percentage_score,
                                double deduction_score, String letterGrade) {
        String updateSql = "UPDATE grade SET absolute_score = ?, percentage_score = ?, deduction_score = ?, letter_grade" +
                " = ? WHERE fk_student = ? AND fk_course = ? AND name = ?";
        try {
//            Connection conn = DBUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(updateSql);
            preparedStatement.setObject(1, absoluate_score);
            preparedStatement.setObject(2, percentage_score);
            preparedStatement.setObject(3, deduction_score);
            preparedStatement.setObject(4, letterGrade);
            preparedStatement.setObject(5, buid);
            preparedStatement.setObject(6, courseid);
            preparedStatement.setObject(7, "final");
            int flag = preparedStatement.executeUpdate();
            preparedStatement.close();
//            conn.close();
            return flag == 0 ? ErrCode.UPDATEERROR.getCode() : ErrCode.OK.getCode();
        } catch (SQLException sqle) {
            return ErrCode.UPDATEERROR.getCode();
        }
    }

    public int addFinalGrade(String courseId, String buid, FinalGrade finalGrade) {
        String addSql = "INSERT INTO grade (fk_student, fk_grading_rule, absolute_score, " +
                "percentage_score, deduction_score, comment, letter_grade, fk_course, name, grade_id) values " +
                "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(addSql);
            preparedStatement.setObject(1, buid);
            preparedStatement.setObject(2, "final");
            preparedStatement.setObject(3, finalGrade.getAbsolute());
            preparedStatement.setObject(4, finalGrade.getPercentage());
            preparedStatement.setObject(5, finalGrade.getDeduction());
            preparedStatement.setObject(6, finalGrade.getComment());
            preparedStatement.setObject(7, finalGrade.getLetterGrade());
            preparedStatement.setObject(8, courseId);
            preparedStatement.setObject(9, "final");
            preparedStatement.setObject(10, buid + courseId + "final");
            int flag = preparedStatement.executeUpdate();
            return flag == 0 ? ErrCode.UPDATEERROR.getCode() : ErrCode.OK.getCode();
        } catch (SQLException sqle) {
            return ErrCode.ADDERROR.getCode();
        }
    }

    public Map<String, Grade> getGradeList(String BUID, String courseId) {
        Map<String, Grade> result = new HashMap<>();
        String selectSql = "SELECT * FROM grade WHERE fk_student = ? and fk_course = ?";
        try {
//            Connection conn = DBUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
            preparedStatement.setObject(1, BUID);
            preparedStatement.setObject(2, courseId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                double absolute_score = resultSet.getDouble("absolute_score");
                double percentage_score = resultSet.getDouble("percentage_score");
                double deduction_score = resultSet.getDouble("deduction_score");
                String comment = resultSet.getString("comment");
                String gradeName = resultSet.getString("name");
                String gradingRuleId = resultSet.getString("fk_grading_rule");
                if (gradeName.equalsIgnoreCase("Final")) {
                    String letter = resultSet.getString("letter_grade");
                    Grade finalGrade = new FinalGrade(gradeName, absolute_score, percentage_score,
                            deduction_score, comment, letter);
                    result.put(gradingRuleId, finalGrade);
                } else {
                    Grade grade = new Grade(gradeName, absolute_score, percentage_score, deduction_score, comment);
                    result.put(gradingRuleId, grade);
                }
            }
            resultSet.close();
            preparedStatement.close();
//            conn.close();
            return result;
        } catch (SQLException sqle) {
            return null;
        }
    }

    public int upgradeGrade(String ruleId, String buid, Grade grade) {
        String updateSql = "REPLACE INTO grade (fk_grading_rule, fk_student, absolute_score, percentage_score, deduction_score, comment, name, fk_course, grade_id)" +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String courseId = CourseDAO.getInstance().getCourseIdByGradingRuleId(ruleId);
        try {
//            Connection conn = DBUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(updateSql);
            preparedStatement.setObject(1, ruleId);
            preparedStatement.setObject(2, buid);
            preparedStatement.setObject(3, grade.getAbsolute());
            preparedStatement.setObject(4, grade.getPercentage());
            preparedStatement.setObject(5, grade.getDeduction());
            preparedStatement.setObject(6, grade.getComment());
            preparedStatement.setObject(7, grade.getRuleId());
            preparedStatement.setObject(8, courseId);
            preparedStatement.setObject(9, buid + courseId + ruleId);
            int flag = preparedStatement.executeUpdate();
            preparedStatement.close();
//            conn.close();
            return flag == 0 ? ErrCode.UPDATEERROR.getCode() : ErrCode.OK.getCode();
        } catch(SQLException sqle) {
            return ErrCode.UPDATEERROR.getCode();
        }
    }

    public int updateGrade(String ruleId, String buid, String comment) {
        String updateSql = "UPDATE grade SET comment = ? WHERE fk_grading_rule = ? AND fk_student = ?";
        try {
//            Connection conn = DBUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(updateSql);
            preparedStatement.setObject(1, comment);
            preparedStatement.setObject(2, ruleId);
            preparedStatement.setObject(3, buid);
            int flag = preparedStatement.executeUpdate();
            preparedStatement.close();
//            conn.close();
            return flag == 0 ? ErrCode.UPDATEERROR.getCode() : ErrCode.OK.getCode();
        } catch (SQLException sqle) {
            return ErrCode.UPDATEERROR.getCode();
        }
    }

    public int deleteGrade(String buid, String courseId, String gradingRuleId) {
        String deleteSql = "DELETE FROM grade WHERE fk_student = ? AND fk_course = ? AND fk_grading_rule = ?";
        try {
//            Connection conn = DBUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(deleteSql);
            preparedStatement.setObject(1, buid);
            preparedStatement.setObject(2, courseId);
            preparedStatement.setObject(3, gradingRuleId);
            return preparedStatement.executeUpdate();
        } catch (SQLException sqle) {
            return ErrCode.DELETEERROR.getCode();
        }
    }

    public int deleteGrade(String courseId) {
        String deleteSql = "DELETE FROM grade WHERE fk_course = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteSql);
            preparedStatement.setObject(1, courseId);
            int flag = preparedStatement.executeUpdate();
            return flag == 0 ? ErrCode.DELETEERROR.getCode() : ErrCode.OK.getCode();
        } catch (SQLException sqle) {
            return ErrCode.DELETEERROR.getCode();
        }
    }

    public int updateGradeList(String buid, String courseid, Map<String, Grade> gradeMap) {
        int flag = 1;
        for(Map.Entry<String, Grade> entry : gradeMap.entrySet()) {
            if(entry.getValue() instanceof FinalGrade) {
                FinalGrade finalGrade = (FinalGrade)entry.getValue();
                flag *= updateFinalGrade(buid, courseid, finalGrade.getAbsolute(),
                        finalGrade.getPercentage(), finalGrade.getDeduction(), finalGrade.getLetterGrade());
            }
            else {
                flag *= upgradeGrade(entry.getKey(), buid, entry.getValue());
            }
        }
//        for(int i = 0; i < grades.size(); i++) {
//            if(grades.get(i) instanceof FinalGrade) {
//                FinalGrade finalGrade = (FinalGrade)grades.get(i);
//                flag *= updateFinalGrade(buid, courseid, finalGrade.getAbsolute(),
//                        finalGrade.getPercentage(), finalGrade.getDeduction(), finalGrade.getLetterGrade());
//            }
//            else {
//                flag *= upgradeGrade(courseid, buid, grades.get(i), gradingRuleId);
//            }
//        }
        return flag;
    }

    public int deleteGradeList(String buid, String courseId) {
        Map<String, Grade> gradeMap = getGradeList(buid, courseId);
        int flag = 1;
        for(Map.Entry<String, Grade> entry : gradeMap.entrySet()) {
            flag *= deleteGrade(buid, courseId, entry.getValue().getRuleId());
        }
        return flag;
    }

    public int addGrade(String courseId, GradingRule gradingRule) {
        List<String> studentId = StudentDAO.getInstance().getStudentIdByCourseId(courseId);
        int updateFlag = 1;
        String updateSql = "REPLACE INTO grade (fk_student, fk_grading_rule, fk_course, name, grade_id, absolute_score, percentage_score" +
                ", deduction_score) values (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            for (String id : studentId) {
//                Connection conn = DBUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(updateSql);
                preparedStatement.setObject(1, id);
                preparedStatement.setObject(2, gradingRule.getId());
                preparedStatement.setObject(3, courseId);
                preparedStatement.setObject(4, gradingRule.getName());
                preparedStatement.setObject(5, id + courseId + gradingRule.getId());
                preparedStatement.setObject(6, gradingRule.getFullScore());
                preparedStatement.setObject(7, 1);
                preparedStatement.setObject(8, 0);
                updateFlag *= preparedStatement.executeUpdate();
            }
            return updateFlag == 0 ? ErrCode.UPDATEERROR.getCode() : ErrCode.OK.getCode();
        } catch (SQLException sqle) {
            return ErrCode.UPDATEERROR.getCode();
        }
    }

    public Grade getGrade(String buid, String ruleId) {
        String selectSql = "SELECT * FROM grade WHERE fk_student = ? AND fk_grading_rule = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
            preparedStatement.setObject(1, buid);
            preparedStatement.setObject(2, ruleId);
            ResultSet resultSet = preparedStatement.executeQuery();
            double absoluteScore = 0;
            double percentageScore = 0;
            double deductionScore = 0;
            String comment = "";
            if(resultSet.next()) {
                absoluteScore = resultSet.getDouble("absolute_score");
                percentageScore = resultSet.getDouble("percentage_score");
                deductionScore = resultSet.getDouble("deduction_score");
                comment = resultSet.getString("comment");
            }
            resultSet.close();
            preparedStatement.close();
            return new Grade(ruleId, absoluteScore, percentageScore, deductionScore, comment);
        } catch (SQLException sqle) {
            return new Grade();
        }
    }

    public List<List<Object>> getGradeFromGradingRule(String courseId) {
        String selectSql = "SELECT * FROM grading_rule WHERE fk_breakdown = ?";
        List<List<Object>> result = new ArrayList<>();
        try {
//            Connection conn = DBUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
            preparedStatement.setObject(1, courseId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                List<Object> temp = new ArrayList<>();
                temp.add(resultSet.getString("name"));
                temp.add(resultSet.getString("grading_rule_id"));
                temp.add(resultSet.getDouble("full_score"));
                result.add(temp);
            }
            resultSet.close();
            preparedStatement.close();
//            conn.close();
            return result;
        } catch(SQLException sqle) {
            return result;
        }
    }
}
