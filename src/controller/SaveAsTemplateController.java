package controller;

import model.Template;
import service.TemplateService;

import java.util.List;
import java.util.Map;

public class SaveAsTemplateController {
    public static boolean isTemplateNameUnique(String name) {
        Map<String, Template> templateMap = TemplateService.getInstance().getTemplateMap();
        for(Template t : templateMap.values()){
            if(t.getName().equals(name)){
                return false;
            }
        }
        return true;
    }

    public static void saveTemplate(String courseId, String templateName){
        TemplateService.getInstance().saveTemplate(courseId,templateName);
    }
}
