package controller;

import service.CourseService;
import service.TemplateService;

import java.util.HashMap;
import java.util.Map;

public class AddCourseController {
    public static Map<String, String> getChooseBreakdownItems(){
        // return items to add into breakdown comboBox, format: Map<breakdownID, name>
        Map<String, String> itemMap = new HashMap();
        if(CourseService.getInstance().getAllCourseName() != null) {
            //TODO 应该是courseID还是courseName？
            itemMap = CourseService.getInstance().getAllCourseName();
        }
        if(TemplateService.getInstance().getAllTemplateName() != null) {
            itemMap.putAll(TemplateService.getInstance().getAllTemplateName());
        }

        return itemMap;
    }
}
