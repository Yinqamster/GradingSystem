package controller;

import service.CourseService;
import service.TemplateService;

import java.util.HashMap;
import java.util.Map;

public class AddCourseController {
    public static Map<String, String> getChooseBreakdownItems(){
        // return items to add into breakdown comboBox, format: Map<breakdownID, name>
        Map<String, String> itemMap = new HashMap(CourseService.getInstance().getAllCourseName());
        itemMap.putAll(TemplateService.getInstance().getAllTemplateName());
        return itemMap;
    }
}
