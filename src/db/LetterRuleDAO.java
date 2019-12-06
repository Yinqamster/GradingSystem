package db;

import utils.ErrCode;

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

    public Map<String, double[]> getLetterMap(String ID, String category) {
        Map<String, double[]> letterResult = new HashMap<>();
        try {
            String preSql = "SELECT * FROM letter_rule WHERE placeholder = ?";
            String selectSql = assambleSql(preSql, category);
            PreparedStatement preparedStatement = DBUtil.getConnection().prepareStatement(selectSql);
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
            DBUtil.getConnection().close();
        } catch (SQLException sqle) {
            return null;
        }
        return letterResult;
    }

    public int addLetterRule(String courseId, String letter, double lower, double upper) {

        return ErrCode.OK.getCode();
    }

    public int editLetterRule(String courseId, String letter, double lower, double upper) {
        return ErrCode.OK.getCode();
    }

    public int updateLetterMap(Map<String, double[]> mapLetter, String ID, String category) {
        int updateFlag = 1;
        String preSql = "REPLACE INTO letter_rule (letter, min_score, max_score, placeholder)" +
                "values (?, ?, ?, ?)";
        String updateSql = assambleSql(preSql, category);
        try {
            for(Map.Entry<String, double[]> entrySet : mapLetter.entrySet()) {
                String letter = entrySet.getKey();
                double[] segment = entrySet.getValue();
                double minScore = Math.min(segment[0], segment[1]);
                double maxScore = Math.max(segment[0], segment[1]);
                PreparedStatement preparedStatement = DBUtil.getConnection().prepareStatement(updateSql);
                preparedStatement.setObject(1, letter);
                preparedStatement.setObject(2, minScore);
                preparedStatement.setObject(3, maxScore);
                preparedStatement.setObject(4, ID);
                updateFlag *= preparedStatement.executeUpdate();
                preparedStatement.close();
                DBUtil.getConnection().close();
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

    public int deleteLetterMap(String ID, String category) {
        String preSql = "DELETE FROM letter_rule WHERE placeholder = ?";
        String deleteSql = assambleSql(preSql, category);
        try {
            PreparedStatement preparedStatement = DBUtil.getConnection().prepareStatement(deleteSql);
            preparedStatement.setObject(1, ID);
            int flag = preparedStatement.executeUpdate();
            return flag == 0 ? ErrCode.DELETEERROR.getCode() : ErrCode.OK.getCode();
        } catch (SQLException sqle) {
            return ErrCode.DELETEERROR.getCode();
        }
    }


//    public Map<String, double[]> getLetterMap(String breakdownID) {
//        Map<String, double[]> letterResult = new HashMap<>();
//        try {
//            String selectSql = "SELECT * FROM letter_rule WHERE fk_breakdown_id = ?";
//            PreparedStatement preparedStatement = DBUtil.getConnection().prepareStatement(selectSql);
//            preparedStatement.setObject(1, breakdownID);
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//            while(resultSet.next()) {
//                String letter = resultSet.getString("letter");
//                double minScore = resultSet.getDouble("min_score");
//                double maxScore = resultSet.getDouble("max_score");
//                double[] segment = {minScore, maxScore};
//                letterResult.put(letter, segment);
//            }
//            resultSet.close();
//            preparedStatement.close();
//            DBUtil.getConnection().close();
//        } catch (SQLException sqle) {
//            return null;
//        }
//        return letterResult;
//    }

//    public int updateLetterMap(Map<String, double[]> mapLetter, String breakdownID) {
//        int updateFlag = 1;
//        String updateSql = "REPLACE INTO letter_rule (letter, min_score, max_score, fk_breakdown_id)" +
//                "values (?, ?, ?, ?)";
//        try {
//            for(Map.Entry<String, double[]> entrySet : mapLetter.entrySet()) {
//                String letter = entrySet.getKey();
//                double[] segment = entrySet.getValue();
//                double minScore = Math.min(segment[0], segment[1]);
//                double maxScore = Math.max(segment[0], segment[1]);
//                PreparedStatement preparedStatement = DBUtil.getConnection().prepareStatement(updateSql);
//                preparedStatement.setObject(1, letter);
//                preparedStatement.setObject(2, minScore);
//                preparedStatement.setObject(3, maxScore);
//                preparedStatement.setObject(4, breakdownID);
//                updateFlag *= preparedStatement.executeUpdate();
//                preparedStatement.close();
//                DBUtil.getConnection().close();
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
//    }

//    public int deleteLetterMap(String breakdownID) {
//        String deleteSql = "DELETE FROM letter_rule WHERE fk_breakdown = ?";
//        try {
//            PreparedStatement preparedStatement = DBUtil.getConnection().prepareStatement(deleteSql);
//            preparedStatement.setObject(1, breakdownID);
//            int flag = preparedStatement.executeUpdate();
//            return flag == 0 ? ErrCode.DELETEERROR.getCode() : ErrCode.OK.getCode();
//        } catch (SQLException sqle) {
//            return ErrCode.DELETEERROR.getCode();
//        }
//    }

    private String assambleSql(String sql, String category) {
       sql =  sql.replaceAll("placeholder", "fk_" + category);
       return sql;
    }
}
