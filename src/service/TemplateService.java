package service;

import model.Breakdown;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TemplateService {

    private Map<String, Breakdown> templateMap;
    private static TemplateService instance;

    public TemplateService() {
        //TODO read template from database
        templateMap = new HashMap<>();
    }

    public static TemplateService getInstance() {
        if (instance == null) {
            instance = new TemplateService();
        }
        return instance;
    }

    public Map<String, Breakdown> getTemplateMap() {
        return templateMap;
    }
    public static Map<String,String> getAllTemplateName() {
        //TODO return name for every saved template, format: Map<breakdownID, templateName>
        return new HashMap<>();
    }
}
