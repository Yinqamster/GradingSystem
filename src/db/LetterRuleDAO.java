package db;

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

    public Map<String, double[]> getLetterMap(String breakdownID) throws SQLException {
        String selectSql = "SELECT * FROM letter_rule WHERE fk_breakdown_id = ?";
        ResultSet resultSet = super.getValue(selectSql, breakdownID);
        Map<String, double[]> letterResult = new HashMap<>();
        while(resultSet.next()) {
            String letter = resultSet.getString("letter");
            double minScore = resultSet.getDouble("min_score");
            double maxScore = resultSet.getDouble("max_score");
            double[] segment = {minScore, maxScore};
            letterResult.put(letter, segment);
        }
        return letterResult;
    }

    public void updateLetterMap(Map<String, double[]> mapLetter, String breakdownID) throws SQLException {
        String updateSql = "REPLACE INTO letter_rule (letter_rule_id, letter, min_score, max_score, fk_breakdown_id)" +
                "values (?, ?, ?, ?, ?)";
        for(Map.Entry<String, double[]> entrySet : mapLetter.entrySet()) {
            String letterID = UUID.randomUUID().toString().substring(0,9);
            String letter = entrySet.getKey();
            double[] segment = entrySet.getValue();
            double minScore = Math.min(segment[0], segment[1]);
            double maxScore = Math.max(segment[0], segment[1]);
            super.update(updateSql, letterID, letter, minScore, maxScore, breakdownID);
        }
    }

    public int deleteLetterMap(String breakdownID) throws SQLException {
        String deleteSql = "DELETE letter_rule WHERE fk_breakdown_id = ?";
        return super.delete(deleteSql, breakdownID);
    }
}
