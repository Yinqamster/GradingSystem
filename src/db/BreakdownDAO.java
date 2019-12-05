package db;

import model.Breakdown;
import model.GradingRule;
import utils.ErrCode;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BreakdownDAO extends DAOImpl{
    public static BreakdownDAO breakdownDAO = new BreakdownDAO();

    private BreakdownDAO() {
    }

    public static BreakdownDAO getInstance() {
        return breakdownDAO;
    }

    public Breakdown getBreakdown(String breakdownID) {
        Map<String, GradingRule> gradingRuleMap = new HashMap<>();
        try {
            String selectSql = "SELECT grading_rule_id FROM grading_rule WHERE fk_breakdown = ? AND" +
                    " parent_id = ?";
            PreparedStatement preparedStatement = DBUtil.getConnection().prepareStatement(selectSql);
            preparedStatement.setObject(1, breakdownID);
            preparedStatement.setObject(2, "");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                String gradingRuleId = resultSet.getString("grading_rule_id");
                gradingRuleMap.put(gradingRuleId, GradingRuleDAO.getInstance().getGradingRule(gradingRuleId));
            }
            resultSet.close();
            preparedStatement.close();
            DBUtil.getConnection().close();
        } catch (SQLException sqle) {
            return null;
        }
        Map<String, double[]> letterMap = LetterRuleDAO.getInstance().getLetterMap(breakdownID);
        return new Breakdown(gradingRuleMap, letterMap, breakdownID);
    }

    public int updateBreakdown(Breakdown breakdown) {
        int gradingRuleUpdateFlag = 1;
        String updateSql = "REPLACE INTO breakdown (break_down_id) values (?)";
        try {
            PreparedStatement preparedStatement = DBUtil.getConnection().prepareStatement(updateSql);
            preparedStatement.setObject(1, breakdown.getBreakdownID());
            gradingRuleUpdateFlag *= preparedStatement.executeUpdate();
        } catch (SQLException sqle) {
            return ErrCode.UPDATEERROR.getCode();
        }
        Map<String, GradingRule> gradingRuleMap = breakdown.getGradingRules();
        for(Map.Entry<String, GradingRule> entry : gradingRuleMap.entrySet()) {
            gradingRuleUpdateFlag *= GradingRuleDAO.getInstance().updateGradingRule(entry.getValue(), breakdown.getBreakdownID());
        }
        return gradingRuleUpdateFlag == 0 ? ErrCode.UPDATEERROR.getCode() : ErrCode.OK.getCode();
    }

    public int deleteBreakdown(Breakdown breakdown) {
        String deleteSql = "DELETE FROM breakdown WHERE break_down_id = ?";
        Map<String, GradingRule> gradingRuleMap = new HashMap<>();
        int deleteFlag = 1;
        try {
            PreparedStatement preparedStatement = DBUtil.getConnection().prepareStatement(deleteSql);
            preparedStatement.setObject(1, breakdown.getBreakdownID());
            deleteFlag *= preparedStatement.executeUpdate();
            preparedStatement.close();
            DBUtil.getConnection().close();
        } catch (SQLException sqle) {
            return ErrCode.DELETEERROR.getCode();
        }
        gradingRuleMap = breakdown.getGradingRules();
        for(Map.Entry<String, GradingRule> entry : gradingRuleMap.entrySet()) {
            deleteFlag *= GradingRuleDAO.getInstance().deleteGradingRule(entry.getKey());
        }
        deleteFlag *= LetterRuleDAO.getInstance().deleteLetterMap(breakdown.getBreakdownID());
        return deleteFlag == 0 ? ErrCode.DELETEERROR.getCode() : ErrCode.OK.getCode();
    }
//TODO
//    public int updateLetterRule() {
//    }
}
