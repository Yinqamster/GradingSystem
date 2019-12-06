package service;

import db.BreakdownDAO;
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
        //TODO read template from database
        templateMap = new HashMap<>();
    }

    public static TemplateService getInstance() {
//        if (instance == null) {
//            instance = new TemplateService();
//        }
        return instance;
    }

    public Map<String, Template> getTemplateMap() {
        return templateMap;
    }
    public Map<String,String> getAllTemplateName() {
        //TODO return name for every saved template, format: Map<breakdownID, templateName>
        Map<String, String> result = new HashMap<>();
        for(Map.Entry<String, Template> m : templateMap.entrySet()) {
            result.put(m.getValue().getBreakdownID(), m.getValue().getName());
        }
        return new HashMap<>();
    }

    public int saveTemplate(String courseId, String templateName) {
        Breakdown breakdown = BreakdownDAO.getInstance().getBreakdown(courseId);

        //TODO save template into db
        return ErrCode.OK.getCode();
    }
}
