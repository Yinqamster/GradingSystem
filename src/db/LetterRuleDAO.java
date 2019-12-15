package db;

import utils.ErrCode;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class LetterRuleDAO {
    public static LetterRuleDAO letterRuleDAO = new LetterRuleDAO();
    private static Connection connection = DBUtil.getInstance();

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
            String preSql = "SELECT * FROM letter_rule WHERE placeholder = ?";
            String selectSql = assambleSql(preSql, category);
            PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
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
        } catch (SQLException sqle) {
            return letterResult;
        }
        return letterResult;
    }

    //TODO
    public int updateBreakdownLetterRule(String courseId, String letter, double lower, double upper, String category) {
//        int updateFlag = 1;
//        String updateLetterSql = "REPLACE INTO letter_rule (letter, min_score, max_score, " +
//                "fk_breakdown) values (?, ?, ?, ?)";
//        String updateBreakdownSql = "REPLACE INTO breakdown (break_down_id, fk_course) values (?, ?)";
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(updateLetterSql);
//            preparedStatement.setObject(1, letter);
//            preparedStatement.setObject(2, lower);
//            preparedStatement.setObject(3, upper);
//            preparedStatement.setObject(4, courseId);
//            updateFlag *= preparedStatement.executeUpdate();
//            preparedStatement.close();
//            preparedStatement = connection.prepareStatement(updateBreakdownSql);
//            preparedStatement.setObject(1, courseId);
//            preparedStatement.setObject(2, courseId);
//            updateFlag *= preparedStatement.executeUpdate();
//            return updateFlag == 0 ? ErrCode.UPDATEERROR.getCode() : ErrCode.OK.getCode();
//        } catch (SQLException sqle) {
//            return ErrCode.UPDATEERROR.getCode();
//        }
        int updateFlag = 1;
        boolean existFlag = false;
        String preUpdateSql = "UPDATE letter_rule SET min_score = ?, max_score = ? WHERE placeholder = ? AND letter = ?";
        String preSelectSql = "SELECT * FROM letter_rule WHERE placeholder = ? AND letter = ?";
        String preInsertSql = "INSERT INTO letter_rule (min_score, max_score, placeholder, letter) values (?, ?, ?, ?)";
        String selectSql = assambleSql(preSelectSql, category);
        String updateSql = assambleSql(preUpdateSql, category);
        String insertSql = assambleSql(preInsertSql, category);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
            preparedStatement.setObject(1, courseId);
            preparedStatement.setObject(2, letter);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                existFlag = true;
            }
            resultSet.close();
            preparedStatement.close();
            if (existFlag) {
                preparedStatement = connection.prepareStatement(updateSql);
            } else {
                preparedStatement = connection.prepareStatement(insertSql);
            }
            preparedStatement.setObject(1, lower);
            preparedStatement.setObject(2, upper);
            preparedStatement.setObject(3, courseId);
            preparedStatement.setObject(4, letter);
            updateFlag *= preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch(SQLException sqle){
                return ErrCode.UPDATEERROR.getCode();
        }
        return updateFlag == 0 ? ErrCode.UPDATEERROR.getCode() : ErrCode.OK.getCode();
    }

    public int addBreakdownLetterRule(String courseId, String letter, double lower, double upper) {
        return updateBreakdownLetterRule(courseId, letter, lower, upper, "breakdown");
    }

    //TODO
    public int editBreakdownLetterRule(String courseId, String letter, double lower, double upper) {
        return updateBreakdownLetterRule(courseId, letter, lower, upper, "breakdown");
    }

    private int updateLetterMap(Map<String, double[]> mapLetter, String ID, String category) {
        int updateFlag = 1;
        for(Map.Entry<String, double[]> entrySet : mapLetter.entrySet()) {
            String letter = entrySet.getKey();
            double[] segment = entrySet.getValue();
            double minScore = Math.min(segment[0], segment[1]);
            double maxScore = Math.max(segment[0], segment[1]);
            updateFlag *= updateBreakdownLetterRule(ID, letter, minScore, maxScore, category);
        }
        return updateFlag == 0 ? ErrCode.UPDATEERROR.getCode() : ErrCode.OK.getCode();
//        boolean existFlag = false;
//        String preUpdateSql = "UPDATE letter_rule SET letter = ?, min_score = ?, max_score = ? WHERE placeholder = ?";
//        String preSelectSql = "SELECT * FROM letter_rule WHERE placeholder = ?";
//        String preInsertSql = "INSERT INTO letter_rule (letter, min_score, max_score, placeholder) values (?, ?, ?, ?)";
//        String selectSql = assambleSql(preSelectSql, category);
//        String updateSql = assambleSql(preUpdateSql, category);
//        String insertSql = assambleSql(preInsertSql, category);
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
//            preparedStatement.setObject(1, ID);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            if(resultSet.next()) {
//                existFlag = true;
//            }
//            resultSet.close();
//            preparedStatement.close();
//            for(Map.Entry<String, double[]> entrySet : mapLetter.entrySet()) {
//                String letter = entrySet.getKey();
//                double[] segment = entrySet.getValue();
//                double minScore = Math.min(segment[0], segment[1]);
//                double maxScore = Math.max(segment[0], segment[1]);
//                if(existFlag) {
//                    preparedStatement = connection.prepareStatement(updateSql);
//                }
//                else {
//                    preparedStatement = connection.prepareStatement(insertSql);
//                }
//                preparedStatement.setObject(1, letter);
//                preparedStatement.setObject(2, minScore);
//                preparedStatement.setObject(3, maxScore);
//                preparedStatement.setObject(4, ID);
//                updateFlag *= preparedStatement.executeUpdate();
//                preparedStatement.close();
//            }
//            if(existFlag) {
//                for(Map.Entry<String, double[]> entrySet : mapLetter.entrySet()) {
//                    String letter = entrySet.getKey();
//                    double[] segment = entrySet.getValue();
//                    double minScore = Math.min(segment[0], segment[1]);
//                    double maxScore = Math.max(segment[0], segment[1]);
//                    preparedStatement = connection.prepareStatement(updateSql);
//                    preparedStatement.setObject(1, letter);
//                    preparedStatement.setObject(2, minScore);
//                    preparedStatement.setObject(3, maxScore);
//                    preparedStatement.setObject(4, ID);
//                    updateFlag *= preparedStatement.executeUpdate();
//                    preparedStatement.close();
//                }
//            }
//            else {
//                for(Map.Entry<String, double[]> entrySet : mapLetter.entrySet()) {
//                    String letter = entrySet.getKey();
//                    double[] segment = entrySet.getValue();
//                    double minScore = Math.min(segment[0], segment[1]);
//                    double maxScore = Math.max(segment[0], segment[1]);
//                    preparedStatement = connection.prepareStatement(insertSql);
//                    preparedStatement.setObject(1, letter);
//                    preparedStatement.setObject(2, minScore);
//                    preparedStatement.setObject(3, maxScore);
//                    preparedStatement.setObject(4, ID);
//                    updateFlag *= preparedStatement.executeUpdate();
//                    preparedStatement.close();
//                }
//            }
//        } catch (SQLException sqle) {
//            return ErrCode.UPDATEERROR.getCode();
//        }
//        if(updateFlag == 0) {
//            return ErrCode.UPDATEERROR.getCode();
//        }
//        else {
//            return ErrCode.OK.getCode();
//        }
    }

    private int deleteLetterMap(String ID, String category) {
        String preSql = "DELETE FROM letter_rule WHERE placeholder = ?";
        String deleteSql = assambleSql(preSql, category);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteSql);
            preparedStatement.setObject(1, ID);
            int flag = preparedStatement.executeUpdate();
            preparedStatement.close();
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
