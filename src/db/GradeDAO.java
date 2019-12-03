package db;

import model.FinalGrade;
import model.Grade;
import model.GradingRule;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GradeDAO {

    public static GradeDAO gradeDAO = new GradeDAO();

    private GradeDAO() {
    }

    public static GradeDAO getInstance() {
        return gradeDAO;
    }

    public Map<String, Grade> getGradeList(String BUID) throws SQLException {
        Map<String, Grade> result = new HashMap<>();
        String selectSql = "SELECT * FROM grade WHERE fk_student = ?";
        PreparedStatement preparedStatement = DBUtil.getConnection().prepareStatement(selectSql);
        preparedStatement.setObject(1, BUID);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()) {
            double absolute_score = resultSet.getDouble("absolute_score");
            double percentage_score = resultSet.getDouble("percentage_score");
            double deduction_score = resultSet.getDouble("deduction_score");
            String comment = resultSet.getString("comment");
            String gradeName = resultSet.getString("grading_rule_id");
            if(gradeName.equalsIgnoreCase("Final")) {
                String letter = resultSet.getString("letter_grade");
                Grade finalGrade = new FinalGrade(gradeName, absolute_score, percentage_score,
                        deduction_score, comment, letter);
                result.put(gradeName, finalGrade);
            }
            else {
                Grade grade = new Grade(gradeName, absolute_score, percentage_score, deduction_score, comment);
                result.put(gradeName, grade);
            }
        }
        return result;
    }

    public int updateGrade(GradingRule gradingRule) throws SQLException {
        String currentId = gradingRule.getId();
        String parentId = gradingRule.getParentID();
        String name = gradingRule.getName();
        double fullScore = gradingRule.getFullScore();
        double proportion = gradingRule.getProportion();
        String childId ="";
        List<GradingRule> gradingRuleList = gradingRule.getChildrenID();
        String updateSql = "REPLACE INTO grading_rule (name, full_score, proportion, " +
                "parent_id, child_id, current_id, grading_rule_id) values (?, ?, ?, ?, ?, ?, ?)";
        if(gradingRuleList.size() != 0) {
            for(int i = 0; i < gradingRuleList.size(); i++) {
                childId = gradingRuleList.get(i).getId();
                int returnValue = updateGrade(gradingRuleList.get(i));
            }
        }
        else {

        }
        return 0;
    }
}
