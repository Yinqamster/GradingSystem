package db;

import utils.ErrCode;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class LetterRuleDAO extends DAOImpl {
    public static LetterRuleDAO letterRuleDAO = new LetterRuleDAO();

    private LetterRuleDAO() {
    }

    public static LetterRuleDAO getInstance() {
        return letterRuleDAO;
    }

    public Map<String, double[]> getTemplateLetterMap(String ID) {
        return getLetterMap(ID, "template");
    }

    public Map<String, double[]> getBreakdownLetterMap(String ID) {
        return getLetterMap(ID, "breakdown");
    }

    public int updateTemplateLetterMap(Map<String, double[]> mapLetter, String ID) {
        return updateLetterMap(mapLetter, ID, "template");
    }

    public int updateBreakdownLetterMap(Map<String, double[]> mapLetter, String ID) {
        return updateLetterMap(mapLetter, ID, "breakdown");
    }

    public int deleteTemplateLetterMap(String ID) {
        return deleteLetterMap(ID, "template");
    }

    public int deleteBreakdownLetterMap(String ID) {
        return deleteLetterMap(ID, "breakdown");
    }

    private Map<String, double[]> getLetterMap(String ID, String category) {
        Map<String, double[]> letterResult = new HashMap<>();
        try {
            Connection conn = DBUtil.getConnection();
            String preSql = "SELECT * FROM letter_rule WHERE placeholder = ?";
            String selectSql = assambleSql(preSql, category);
            PreparedStatement preparedStatement = conn.prepareStatement(selectSql);
            preparedStatement.setObject(1, ID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                String letter = resultSet.getString("letter");
                double minScore = resultSet.getDouble("min_score");
                double maxScore = resultSet.getDouble("max_score");
                double[] segment = {minScore, maxScore};
                letterResult.put(letter, segment);
            }
            resultSet.close();
            preparedStatement.close();
            conn.close();
        } catch (SQLException sqle) {
            return letterResult;
        }
        return letterResult;
    }

    //TODO
    public int updateBreakdownLetterRule(String courseId, String letter, double lower, double upper) {
        int updateFlag = 1;
        String updateLetterSql = "REPLACE INTO letter_rule (letter, min_score, max_score, " +
                "fk_breakdown) values (?, ?, ?, ?)";
        String updateBreakdownSql = "REPLACE INTO breakdown (break_down_id, fk_course) values (?, ?)";
        try {
            Connection conn = DBUtil.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(updateLetterSql);
            preparedStatement.setObject(1, letter);
            preparedStatement.setObject(2, lower);
            preparedStatement.setObject(3, upper);
            preparedStatement.setObject(4, courseId);
            updateFlag *= preparedStatement.executeUpdate();
            preparedStatement.close();
            preparedStatement = conn.prepareStatement(updateBreakdownSql);
            preparedStatement.setObject(1, courseId);
            preparedStatement.setObject(2, courseId);
            updateFlag *= preparedStatement.executeUpdate();
            conn.close();
            return updateFlag == 0 ? ErrCode.UPDATEERROR.getCode() : ErrCode.OK.getCode();
        } catch (SQLException sqle) {
            return ErrCode.UPDATEERROR.getCode();
        }
    }

    public int addBreakdownLetterRule(String courseId, String letter, double lower, double upper) {
        return updateBreakdownLetterRule(courseId, letter, lower, upper);
    }

    //TODO
    public int editBreakdownLetterRule(String courseId, String letter, double lower, double upper) {
        return updateBreakdownLetterRule(courseId, letter, lower, upper);
    }

    private int updateLetterMap(Map<String, double[]> mapLetter, String ID, String category) {
        int updateFlag = 1;
        String preSql = "REPLACE INTO letter_rule (letter, min_score, max_score, placeholder)" +
                "values (?, ?, ?, ?)";
        String updateSql = assambleSql(preSql, category);
        try {
            Connection conn = DBUtil.getConnection();
            for(Map.Entry<String, double[]> entrySet : mapLetter.entrySet()) {
                String letter = entrySet.getKey();
                double[] segment = entrySet.getValue();
                double minScore = Math.min(segment[0], segment[1]);
                double maxScore = Math.max(segment[0], segment[1]);
                PreparedStatement preparedStatement = conn.prepareStatement(updateSql);
                preparedStatement.setObject(1, letter);
                preparedStatement.setObject(2, minScore);
                preparedStatement.setObject(3, maxScore);
                preparedStatement.setObject(4, ID);
                updateFlag *= preparedStatement.executeUpdate();
                preparedStatement.close();
                conn.close();
            }
        } catch (SQLException sqle) {
            return ErrCode.UPDATEERROR.getCode();
        }
        if(updateFlag == 0) {
            return ErrCode.UPDATEERROR.getCode();
        }
        else {
            return ErrCode.OK.getCode();
        }
    }

    private int deleteLetterMap(String ID, String category) {
        String preSql = "DELETE FROM letter_rule WHERE placeholder = ?";
        String deleteSql = assambleSql(preSql, category);
        try {
            Connection conn = DBUtil.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(deleteSql);
            preparedStatement.setObject(1, ID);
            int flag = preparedStatement.executeUpdate();
            preparedStatement.close();
            conn.close();
            return flag == 0 ? ErrCode.DELETEERROR.getCode() : ErrCode.OK.getCode();
        } catch (SQLException sqle) {
            return ErrCode.DELETEERROR.getCode();
        }
    }

    private String assambleSql(String sql, String category) {
        sql =  sql.replaceAll("placeholder", "fk_" + category);
        return sql;
    }
}
