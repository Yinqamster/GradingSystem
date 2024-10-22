package db;

import model.Breakdown;
import model.GradingRule;
import utils.ErrCode;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *@author Jiaqian Sun
 */

public class BreakdownDAO{
    public static BreakdownDAO breakdownDAO = new BreakdownDAO();
    private static Connection connection = DBUtil.getInstance();

    public BreakdownDAO() {
    }

    public static BreakdownDAO getInstance() {
        return breakdownDAO;
    }

    // get breakdown via breakdown id
    public Breakdown getBreakdown(String breakdownID) {
        Map<String, GradingRule> gradingRuleMap = new HashMap<>();
        try {
            String selectSql = "SELECT grading_rule_id FROM grading_rule WHERE fk_breakdown = ? AND" +
                    " parent_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
            preparedStatement.setObject(1, breakdownID);
            preparedStatement.setObject(2, "");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                String gradingRuleId = resultSet.getString("grading_rule_id");
                gradingRuleMap.put(gradingRuleId, GradingRuleDAO.getInstance().getGradingRule(gradingRuleId));
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException sqle) {
            return new Breakdown();
        }
        Map<String, double[]> letterMap = LetterRuleDAO.getInstance().getBreakdownLetterMap(breakdownID);
        return new Breakdown(gradingRuleMap, letterMap, breakdownID);
    }

    // update a breakdown
    public int updateBreakdown(Breakdown breakdown) {
        int gradingRuleUpdateFlag = 1;
        String updateSql = "REPLACE INTO breakdown (break_down_id, fk_course) values (?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(updateSql);
            preparedStatement.setObject(1, breakdown.getBreakdownID());
            preparedStatement.setObject(2, breakdown.getBreakdownID());
            gradingRuleUpdateFlag *= preparedStatement.executeUpdate();
        } catch (SQLException sqle) {
            return ErrCode.UPDATEERROR.getCode();
        }
        Map<String, GradingRule> gradingRuleMap = breakdown.getGradingRules();
        for(Map.Entry<String, GradingRule> entry : gradingRuleMap.entrySet()) {
            gradingRuleUpdateFlag *= GradingRuleDAO.getInstance().updateBreakdownGradingRule(entry.getValue(), breakdown.getBreakdownID());
        }
        gradingRuleUpdateFlag *= LetterRuleDAO.getInstance().updateBreakdownLetterMap(breakdown.getLetterRule(), breakdown.getBreakdownID());
        return gradingRuleUpdateFlag == 0 ? ErrCode.UPDATEERROR.getCode() : ErrCode.OK.getCode();
    }

    // delete a breakdown
    public int deleteBreakdown(String breakdownId) {
        String selectSql = "SELECT grading_rule_id FROM grading_rule WHERE fk_breakdown = ?";
        String deleteSql = "DELETE FROM breakdown WHERE break_down_id = ?";
        List<String> deleteGradingList = new ArrayList<>();
        int deleteFlag = 1;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
            preparedStatement.setObject(1, breakdownId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                String gradingId = resultSet.getString("grading_rule_id");
                deleteGradingList.add(gradingId);
            }
            preparedStatement.close();
            preparedStatement = connection.prepareStatement(deleteSql);
            preparedStatement.setObject(1, breakdownId);
            deleteFlag *= preparedStatement.executeUpdate();
            resultSet.close();
            preparedStatement.close();
            for(String str : deleteGradingList) {
                deleteFlag *= GradingRuleDAO.getInstance().deleteGradingRule(str);
            }
            LetterRuleDAO.getInstance().deleteBreakdownLetterMap(breakdownId);
        } catch (SQLException sqle) {
            return ErrCode.DELETEERROR.getCode();
        }
        return deleteFlag == 0 ? ErrCode.DELETEERROR.getCode() : ErrCode.OK.getCode();
    }
}
