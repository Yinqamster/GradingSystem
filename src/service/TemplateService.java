package service;

import db.BreakdownDAO;
import db.TemplateDAO;
import model.Breakdown;
import model.GradingRule;
import model.Template;
import utils.ErrCode;

import java.util.*;

public class TemplateService {

    //template name, template
    private static Map<String, Template> templateMap;
    private static TemplateService instance = new TemplateService();


    private TemplateService() {
        templateMap = new HashMap<>();
    }

    public static TemplateService getInstance() {
//        if (instance == null) {
//            instance = new TemplateService();
//        }
        return instance;
    }

    public Map<String, Template> getTemplateMap() {
        templateMap = TemplateDAO.getInstance().getTemplateMap();
        return templateMap;
    }
    public Map<String,String> getAllTemplateName() {
        // return name for every saved template, format: Map<breakdownID, templateName>
        Map<String, String> result = new HashMap<>();
        for(Map.Entry<String, Template> m : templateMap.entrySet()) {
            result.put(m.getValue().getBreakdownID(), m.getValue().getName());
        }
        return result;
    }

    public Template getTemplateById(String templateId) {
        return TemplateDAO.getInstance().getTemplate(templateId);
    }

    public void generateGradingRuleID(GradingRule gradingRule, String parentId){
        gradingRule.setId(UUID.randomUUID().toString());
        gradingRule.setParentID(parentId);
        if(gradingRule.getChildren() != null && gradingRule.getChildren().size() != 0) {
            for(GradingRule g : gradingRule.getChildren()) {
                generateGradingRuleID(g, gradingRule.getId());
            }
        }
    }

    public int saveTemplate(String courseId, String templateName) {
        Breakdown breakdown = BreakdownDAO.getInstance().getBreakdown(courseId);

        Map<String, GradingRule> gradingRuleMap = breakdown.getGradingRules();
        Map<String, GradingRule> newGradingRuleMap = new HashMap<>();
        for(GradingRule gradingRule : gradingRuleMap.values()) {
            generateGradingRuleID(gradingRule, "");
            newGradingRuleMap.put(gradingRule.getId(), gradingRule);
        }
        breakdown.setGradingRules(newGradingRuleMap);


        Template template = new Template(breakdown.getBreakdownID(), templateName, breakdown.getGradingRules(), breakdown.getLetterRule());
        return TemplateDAO.getInstance().updateTemplate(template);
    }
}
