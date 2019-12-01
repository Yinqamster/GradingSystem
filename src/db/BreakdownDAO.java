package db;

import model.Breakdown;
import model.GradingRule;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BreakdownDAO extends DAOImpl{
    public static BreakdownDAO breakdownDAO = new BreakdownDAO();

    private BreakdownDAO() {
    }

    public static BreakdownDAO getInstance() {
        return breakdownDAO;
    }

    public Breakdown getBreakdown(String breakdownID) throws SQLException {
        String selectGradingRule = "SELECT * FROM breakdown WHERE break_down_id = ?";
        ResultSet resultSet = super.getValue(selectGradingRule, breakdownID);
        List<GradingRule> gradingRuleList = GradingRuleDAO.getInstance().getGradingRuleList(breakdownID);
        Map<String, double[]> letterMap = LetterRuleDAO.getInstance().getLetterMap(breakdownID);
        return new Breakdown(gradingRuleList, letterMap, breakdownID);
    }

    public int updateBreakdown(Breakdown breakdown) throws SQLException {
        String updateSql = "REPLACE INTO breakdown (break_down_id) values (?)";
        return super.update(updateSql, breakdown.getBreakdownID());
    }

    public int deleteBreakdown(Breakdown breakdown) throws SQLException {
        String deleteSql = "DELETE breakdown WHERE break_down_id = ?";
        return super.delete(deleteSql, breakdown.getBreakdownID());
    }
}
