package db;
import model.GradingRule;
import utils.ErrCode;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GradingRuleDAO {

    private static GradingRuleDAO gradingRuleDAO = new GradingRuleDAO();

    private static Connection connection = DBUtil.getInstance();

    public static GradingRuleDAO getInstance() {
        return gradingRuleDAO;
    }

    private GradingRuleDAO() {
    }

    public int updateTemplateGradingRule(GradingRule gradingRule, String breakdownId) {
        return updateGradingRule(gradingRule, breakdownId, "template");
    }

    public int updateBreakdownGradingRule(GradingRule gradingRule, String breakdownId) {
        return updateGradingRule(gradingRule, breakdownId, "breakdown");
    }

    public GradingRule getGradingRule(String currentRuleId) {
        return this.getGradingRuleHelper(currentRuleId, 0);
    }

    private GradingRule getGradingRuleHelper(String currentId, int depth) {
        // Gather the grading rules which parent id equals to current id
        // If get the leaf node, add it to the gradingRuleList;
        // Else recursively go into getGradingRuleHelper method
        try {
//            Connection conn = DBUtil.getConnection();
            List<GradingRule> gradingRuleList = new ArrayList<>();
            String selectSql = "SELECT * FROM grading_rule WHERE parent_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
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
            resultSet.close();
            preparedStatement.close();
            // Get the information of current grading_rule
            selectSql = "SELECT * FROM grading_rule WHERE grading_rule_id = ?";
            preparedStatement = connection.prepareStatement(selectSql);
            preparedStatement.setObject(1, currentId);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                name = resultSet.getString("name");
                fullScore = resultSet.getDouble("full_score");
                proportion = resultSet.getDouble("proportion");
                parentId = resultSet.getString("parent_id");
            }
            resultSet.close();
            preparedStatement.close();
//            conn.close();
            return new GradingRule(currentId, parentId, name, fullScore, proportion, gradingRuleList);
        } catch (SQLException sqle) {
            return null;
        }
    }

    private int updateGradingRule(GradingRule gradingRule, String breakdownId, String category) {
        int updateFlag = 1;
        List<List<Object>> infoList = new ArrayList<>();
        getUpdateList(gradingRule, infoList, breakdownId);
        try {
            for(int i = 0; i < infoList.size(); i++) {
//                Connection conn = DBUtil.getConnection();
                String preSql = "REPLACE INTO grading_rule (name, full_score, proportion, " +
                        "parent_id, placeholder, grading_rule_id) values (?, ?, " +
                        "?, ?, ?, ?)";
                String updateSql = assambleSql(preSql, category);
                PreparedStatement preparedStatement = connection.prepareStatement(updateSql);
                List<Object> temp = infoList.get(i);
                for(int j = 0; j < temp.size(); j++) {
                    preparedStatement.setObject(j + 1, temp.get(j));
                }
                updateFlag *= preparedStatement.executeUpdate();
                preparedStatement.close();
//                conn.close();
                String name = temp.get(0).toString();
                String gradingRuleId = temp.get(5).toString();
                System.out.println("name: " + name);
                System.out.println("gradingruleid: " + gradingRuleId);
                System.out.println("courseid: " + breakdownId);
                GradeDAO.getInstance().addGrade(breakdownId, gradingRuleId, name);
            }
//            Connection conn = DBUtil.getConnection();
            String updateBreakdownSql = "REPLACE INTO breakdown (break_down_id, fk_course) values (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(updateBreakdownSql);
            preparedStatement.setObject(1, breakdownId);
            preparedStatement.setObject(2, breakdownId);
            updateFlag *= preparedStatement.executeUpdate();
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
//                Connection conn = DBUtil.getConnection();
                String deleteSql = "DELETE FROM grading_rule WHERE grading_rule_id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(deleteSql);
                preparedStatement.setObject(1, deleteList.get(i));
                returnValue *= preparedStatement.executeUpdate();
                preparedStatement.close();
//                conn.close();
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
//            Connection conn = DBUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
            preparedStatement.setObject(1, gradingRuleID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                String currentId = resultSet.getString("grading_rule_id");
                deleteRuleList.add(resultSet.getString("grading_rule_id"));
                getDeleteRuleList(currentId, deleteRuleList);
            }
            resultSet.close();
            preparedStatement.close();
//            conn.close();
        } catch (SQLException sqle) {
            System.err.println(sqle);
        }
    }

    public List<String> getGradingRuleList(String courseId) {
        String selectSql = "SELECT * FROM grading_rule WHERE fk_breakdown = ?";
//        Connection conn = DBUtil.getConnection();
        List<String> result = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
            preparedStatement.setObject(1, courseId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                String gradingRuleId = resultSet.getString("grading_rule_id");
                result.add(gradingRuleId);
            }
            return result;
        } catch(SQLException sqle) {
            return result;
        }
    }

    private String assambleSql(String sql, String category) {
        sql = sql.replace("placeholder", "fk_" + category);
        return sql;
    }
}
