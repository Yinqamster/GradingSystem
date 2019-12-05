package db;
import com.sun.tools.internal.xjc.ErrorReceiver;
import model.GradingRule;
import utils.ErrCode;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GradingRuleDAO {

    private static GradingRuleDAO gradingRuleDAO = new GradingRuleDAO();

    public static GradingRuleDAO getInstance() {
        return gradingRuleDAO;
    }

    private GradingRuleDAO() {

    }

    public GradingRule getGradingRule(String currentRuleId) {
        return this.getGradingRuleHelper(currentRuleId, 0);
    }

    private GradingRule getGradingRuleHelper(String currentId, int depth) {
        // Gather the grading rules which parent id equals to current id
        // If get the leaf node, add it to the gradingRuleList;
        // Else recursively go into getGradingRuleHelper method
        try {
            List<GradingRule> gradingRuleList = new ArrayList<>();
            String selectSql = "SELECT * FROM grading_rule WHERE parent_id = ?";
            PreparedStatement preparedStatement = DBUtil.getConnection().prepareStatement(selectSql);
            preparedStatement.setObject(1, currentId);
            ResultSet resultSet = preparedStatement.executeQuery();
            String name = "";
            double fullScore = 0;
            double proportion = 0;
            String tempId = "";
            String parentId = "";
            while(resultSet.next()) {
                name = resultSet.getString("name");
                fullScore = resultSet.getDouble("full_score");
                proportion = resultSet.getDouble("proportion");
                tempId = resultSet.getString("grading_rule_id");
                gradingRuleList.add(getGradingRuleHelper(tempId, depth + 1));
            }
            // Get the information of current grading_rule
            selectSql = "SELECT * FROM grading_rule WHERE current_id = ?";
            preparedStatement = DBUtil.getConnection().prepareStatement(selectSql);
            preparedStatement.setObject(1, currentId);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                name = resultSet.getString("name");
                fullScore = resultSet.getDouble("full_score");
                proportion = resultSet.getDouble("proportion");
                parentId = resultSet.getString("parent_id");
            }
            resultSet.close();
            return new GradingRule(currentId, parentId, name, fullScore, proportion, gradingRuleList);
        } catch (SQLException sqle) {
            return null;
        }
    }

    public int updateGradingRule(GradingRule gradingRule, String breakdownId) {
        int updateFlag = 1;
        List<List<Object>> infoList = new ArrayList<>();
        getUpdateList(gradingRule, infoList, breakdownId);
        try {
            for(int i = 0; i < infoList.size(); i++) {
                String updateSql = "REPLACE INTO grading_rule (name, full_score, proportion, " +
                        "parent_id, fk_breakdown, current_id, grading_rule_id) values (?, ?, " +
                        "?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = DBUtil.getConnection().prepareStatement(updateSql);
                List<Object> temp = infoList.get(i);
                for(int j = 0; j < temp.size(); j++) {
                    preparedStatement.setObject(j + 1, temp.get(j));
                }
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

    private void getUpdateList(GradingRule gradingRule, List<List<Object>> infoList, String breakdownId) {
        List<Object> temp = new ArrayList<>();
        // name, fullscore, proportion, parentid, breakdownid, currentid
        temp.add(gradingRule.getName());
        temp.add(gradingRule.getFullScore());
        temp.add(gradingRule.getProportion());
        temp.add(gradingRule.getParentID());
        temp.add(breakdownId);
        temp.add(gradingRule.getId());
        temp.add(gradingRule.getName() + breakdownId);
        infoList.add(temp);
        for(int i = 0; i < gradingRule.getChildren().size(); i++) {
            getUpdateList(gradingRule.getChildren().get(i), infoList, breakdownId);
        }
    }

    public int deleteGradingRule(String gradingRuleID) {
        int returnValue = 1;
        List<String> deleteList = new ArrayList<>();
        deleteList.add(gradingRuleID);
        getDeleteRuleList(gradingRuleID, deleteList);
        try {
            for(int i = 0; i < deleteList.size(); i++) {
                String deleteSql = "DELETE FROM grading_rule WHERE grading_rule_id = ?";
                PreparedStatement preparedStatement = DBUtil.getConnection().prepareStatement(deleteSql);
                preparedStatement.setObject(1, deleteList.get(i));
                returnValue *= preparedStatement.executeUpdate();
                preparedStatement.close();
                DBUtil.getConnection().close();
            }
        } catch (SQLException sqle) {
            return ErrCode.DELETEERROR.getCode();
        }
        if(returnValue == 0) {
            return ErrCode.DELETEGRADINGRULEERROR.getCode();
        }
        else {
            return ErrCode.OK.getCode();
        }
    }

    private void getDeleteRuleList(String gradingRuleID, List<String> deleteRuleList){
        String selectSql = "SELECT * FROM grading_rule WHERE parent_id = ?";
        try {
            PreparedStatement preparedStatement = DBUtil.getConnection().prepareStatement(selectSql);
            preparedStatement.setObject(1, gradingRuleID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                String currentId = resultSet.getString("grading_rule_id");
                deleteRuleList.add(resultSet.getString("grading_rule_id"));
                getDeleteRuleList(currentId, deleteRuleList);
            }
        } catch (SQLException sqle) {
            System.err.println(sqle);
        }
    }
}
