package service;

import db.BreakdownDAO;
import db.TemplateDAO;
import model.Breakdown;
import model.Template;
import utils.ErrCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        return new HashMap<>();
    }

    public Template getTemplateById(String templateId) {
        return TemplateDAO.getInstance().getTemplate(templateId);
    }

    public int saveTemplate(String courseId, String templateName) {
        Breakdown breakdown = BreakdownDAO.getInstance().getBreakdown(courseId);
        Template template = new Template(breakdown.getBreakdownID(), templateName, breakdown.getGradingRules(), breakdown.getLetterRule());
        return TemplateDAO.getInstance().addTemplate(courseId, template);
    }
}
