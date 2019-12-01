package db;

import model.GradingRule;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GradingRuleDAO extends DAOImpl{

    private static GradingRuleDAO gradingRuleDAO = new GradingRuleDAO();

    public static GradingRuleDAO getInstance() {
        return gradingRuleDAO;
    }

    private GradingRuleDAO() {

    }

    public GradingRule getGradingRule(String gradingRuleId) throws SQLException {
        String selectSql = "SELECT * FROM grading_rule WHERE grading_rule_id = ?";
        ResultSet resultSet = super.getValue(selectSql, gradingRuleId);
        String name = resultSet.getString("name");
        double fullScore = resultSet.getDouble("full_score");
        double proportion = resultSet.getDouble("proportion");
        String parentID = resultSet.getString("parent_id");
        String gradingRuleID = resultSet.getString("grading_rule_id");
        return new GradingRule(gradingRuleID, parentID, name, fullScore, proportion);
    }

    public int updateGradingRule(GradingRule gradingRule) throws SQLException{
        String updateSql = "REPLACE INTO grading_rule (name, full_score, proportion, " +
                "grading_rule_id, parent_id) values (?, ?, ?, ?, ?)";
        String name = gradingRule.getName();
        double fullScore = gradingRule.getFullScore();
        String gradingRuleID = gradingRule.getId();
        double proportion = gradingRule.getProportion();
        String parentID = gradingRule.getParentID();
        return super.update(updateSql, name, fullScore, proportion, gradingRuleID, parentID);
    }

    public int deleteGradingRule(String gradingRuleID) throws SQLException {
        String deleteSql = "DELETE FROM grading_rule WHERE grading_rule_id = ?";
        return super.delete(deleteSql, gradingRuleID);
    }

    public List<GradingRule> getGradingRuleList(String breakdownID) throws SQLException {
        String selectSql = "SELECT * FROM grading_rule WHERE fk_breakdown = ?";
        ResultSet resultSet = super.getValue(selectSql, breakdownID);
        List<GradingRule> result = new ArrayList<>();
        while(resultSet.next()) {
            String name = resultSet.getString("name");
            double fullScore = resultSet.getDouble("full_score");
            double proportion = resultSet.getDouble("proportion");
            String gradingRuleID = resultSet.getString("grading_rule_id");
            String parentID = resultSet.getString("parent_id");
            result.add(new GradingRule(gradingRuleID, parentID, name, fullScore, proportion));
        }
        return result;
    }
}
