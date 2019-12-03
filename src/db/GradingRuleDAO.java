package db;

//import com.sun.org.apache.bcel.internal.generic.ANEWARRAY;
import model.GradingRule;
import utils.ErrCode;

import java.sql.PreparedStatement;
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

    public GradingRule getGradingRule(String currentRuleId) throws SQLException {
        return this.getGradingRuleHelper(currentRuleId, 0);
    }

    private GradingRule getGradingRuleHelper(String currentId, int depth) throws SQLException {
        // Gather the grading rules which parent id equals to current id
        // If get the leaf node, add it to the gradingRuleList;
        // Else recursively go into getGradingRuleHelper method
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
            tempId = resultSet.getString("current_id");
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
        return new GradingRule(currentId, parentId, name, fullScore, proportion, gradingRuleList);
    }

    public int updateGradingRule(GradingRule gradingRule, String breakdownId) throws SQLException {
        List<List<Object>> infoList = new ArrayList<>();
        getUpdateList(gradingRule, infoList, breakdownId);
        for(int i = 0; i < infoList.size(); i++) {
            String updateSql = "REPLACE INTO grading_rule (name, full_score, proportion, " +
                    "parent_id, fk_breakdown, current_id, grading_rule_id) values (?, ?, " +
                    "?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = DBUtil.getConnection().prepareStatement(updateSql);
            List<Object> temp = infoList.get(i);
            for(int j = 0; j < temp.size(); j++) {
                preparedStatement.setObject(j + 1, temp.get(j));
            }
            preparedStatement.executeUpdate();
        }
        return 0;
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
        for(int i = 0; i < gradingRule.getChildrenID().size(); i++) {
            getUpdateList(gradingRule.getChildrenID().get(i), infoList, breakdownId);
        }
    }

    public int deleteGradingRule(String gradingRuleID) throws SQLException {
        int returnValue = 1;
        List<String> deleteList = new ArrayList<>();
        getDeleteRuleList(gradingRuleID, deleteList);
        for(int i = 0; i < deleteList.size(); i++) {
            String deleteSql = "DELETE FROM grading_rule WHERE grading_rule_id = ?";
            PreparedStatement preparedStatement = DBUtil.getConnection().prepareStatement(deleteSql);
            preparedStatement.setObject(1, deleteList.get(i));
            returnValue *= preparedStatement.executeUpdate();
        }
        if(returnValue == 0) {
            return ErrCode.DELETEGRADINGRULEERROR.getCode();
        }
        else {
            return ErrCode.OK.getCode();
        }
    }

    private void getDeleteRuleList(String gradingRuleID, List<String> deleteRuleList) throws SQLException{
        String selectSql = "SELECT * FROM grading_rule WHERE parent_id = ?";
        PreparedStatement preparedStatement = DBUtil.getConnection().prepareStatement(selectSql);
        preparedStatement.setObject(1, gradingRuleID);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()) {
            deleteRuleList.add(resultSet.getString("grading_rule_id"));
            String childId = resultSet.getString("child_id");
            getDeleteRuleList(childId, deleteRuleList);
        }
    }
}
