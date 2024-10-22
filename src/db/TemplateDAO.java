package db;

import model.GradingRule;
import model.Template;
import utils.ErrCode;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 *@author Jiaqian Sun
 */

public class TemplateDAO extends BreakdownDAO{
    private static TemplateDAO templateDAO = new TemplateDAO();

    private static Connection connection = DBUtil.getInstance();

    private TemplateDAO() {
    }

    public static TemplateDAO getInstance() {
        return templateDAO;
    }

    public Template getTemplate(String templateId) {
        Map<String, GradingRule> gradingRuleMap = new HashMap<>();
        String templateName = "";
        try {
            String selectSql = "SELECT grading_rule_id FROM grading_rule WHERE fk_template = ? AND" +
                    " parent_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
            preparedStatement.setObject(1, templateId);
            preparedStatement.setObject(2, "");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                String gradingRuleId = resultSet.getString("grading_rule_id");
                gradingRuleMap.put(gradingRuleId, GradingRuleDAO.getInstance().getGradingRule(gradingRuleId));
            }
            resultSet.close();
            preparedStatement.close();
            selectSql = "SELECT name FROM template WHERE template_id = ?";
            preparedStatement = connection.prepareStatement(selectSql);
            preparedStatement.setObject(1, templateId);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                templateName = resultSet.getString("name");
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException sqle) {
            return null;
        }
        Map<String, double[]> letterMap = LetterRuleDAO.getInstance().getTemplateLetterMap(templateId);
        return new Template(templateId, templateName, gradingRuleMap, letterMap);
    }

    public int updateTemplate(Template template) {
        int gradingRuleUpdateFlag = 1;
        String updateSql = "REPLACE INTO template (template_id, name) values (?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(updateSql);
            preparedStatement.setObject(1, template.getBreakdownID());
            preparedStatement.setObject(2, template.getName());
            gradingRuleUpdateFlag *= preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException sqle) {
            return ErrCode.UPDATEERROR.getCode();
        }
        Map<String, GradingRule> gradingRuleMap = template.getGradingRules();
        for(Map.Entry<String, GradingRule> entry : gradingRuleMap.entrySet()) {
            gradingRuleUpdateFlag *= GradingRuleDAO.getInstance().updateTemplateGradingRule(entry.getValue(), template.getBreakdownID());
        }
        gradingRuleUpdateFlag *= LetterRuleDAO.getInstance().updateTemplateLetterMap(template.getLetterRule(), template.getBreakdownID());
        return gradingRuleUpdateFlag == 0 ? ErrCode.UPDATEERROR.getCode() : ErrCode.OK.getCode();
    }

    public int deleteTemplate(Template template) {
        String deleteSql = "DELETE FROM template WHERE template_id = ?";
        Map<String, GradingRule> gradingRuleMap = new HashMap<>();
        int deleteFlag = 1;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteSql);
            preparedStatement.setObject(1, template.getBreakdownID());
            deleteFlag *= preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException sqle) {
            return ErrCode.DELETEERROR.getCode();
        }
        gradingRuleMap = template.getGradingRules();
        for(Map.Entry<String, GradingRule> entry : gradingRuleMap.entrySet()) {
            deleteFlag *= GradingRuleDAO.getInstance().deleteGradingRule(entry.getKey());
        }
        deleteFlag *= LetterRuleDAO.getInstance().deleteTemplateLetterMap(template.getBreakdownID());
        return deleteFlag == 0 ? ErrCode.DELETEERROR.getCode() : ErrCode.OK.getCode();
    }

    public Map<String, Template> getTemplateMap() {
        Map<String, GradingRule> temp = new HashMap<>();
        Map<String, Template> result = new HashMap<>();
        try {
            String selectSql = "SELECT grading_rule_id FROM grading_rule WHERE" +
                    " parent_id = ? and fk_template <> ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
            preparedStatement.setObject(1, "");
            preparedStatement.setObject(2, "");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                String gradingRuleId = resultSet.getString("grading_rule_id");
                temp.put(gradingRuleId, GradingRuleDAO.getInstance().getGradingRule(gradingRuleId));
            }
            resultSet.close();
            preparedStatement.close();
            selectSql = "SELECT grading_rule_id, template.name, template_id FROM grading_rule inner join template ON grading_rule.fk_template = template.template_id AND grading_rule.parent_id = ?";
            preparedStatement = connection.prepareStatement(selectSql);
            preparedStatement.setObject(1, "");
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                String gradingRuleId = resultSet.getString("grading_rule_id");
                String name = resultSet.getString("name");
                String templateId = resultSet.getString("template_id");
                Map<String, double[]> letterMap = LetterRuleDAO.getInstance().getTemplateLetterMap(templateId);
                if(result.containsKey(name)) {
                    result.get(name).getGradingRules().put(gradingRuleId, temp.get(gradingRuleId));
                }
                else {
                    Template template = new Template(templateId, name, letterMap);
                    template.getGradingRules().put(gradingRuleId, temp.get(gradingRuleId));
                    result.put(name, template);
                }
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException sqle) {
            return new HashMap<>();
        }
        return result;
    }

    //TODO
    public int addTemplate(String courseId, Template template) {

        return ErrCode.OK.getCode();
    }
}
