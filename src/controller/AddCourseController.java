package controller;

import model.Course;
import model.Template;
import service.CourseService;
import service.TemplateService;
import utils.Config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddCourseController {
    public static Map<String,String> getChooseBreakdownItems(){
        // return items to add into breakdown comboBox, format: Map<breakdownID, name>
        Map<String,String> itemMap = new HashMap(); // breakdownID/templateName, item
        List<Course> courseList = CourseService.getInstance().getAllCourses();
        if(courseList.size() != 0) {
            for(Course course : courseList){
                String item = "Course: " + course.getName() + " - " + course.getSection() + " - " + course.getSemester();
                if(course.getBreakdown()!=null) {
                    itemMap.put(course.getBreakdown().getBreakdownID(), item);
                }
            }
        }
        List<Template> templateList = new ArrayList<>(TemplateService.getInstance().getTemplateMap().values());
        if(templateList.size() != 0) {
            for(Template template : templateList) {
                String item = "Template: " + template.getName();
                itemMap.put(template.getName(), item);
            }
        }

        return itemMap;
    }

    public static int getItemType(String itemString){
        String type = itemString.split(" ")[0].replace(":","");
        int res = 0;
        if(type.equals("Course")){
            res = Config.BREAKDOWN;
        }else if(type.equals("Template")){
            res = Config.TEMPLATE;
        }
        return res;
    }

    // if course, return breakdownID, if template, return templateName
    public static String getBreakdownID(String item, Map<String,String> itemMap){
        for (Map.Entry<String, String> entry : itemMap.entrySet()) {
            if(entry.getValue().equals(item)){
                return entry.getKey();
            }
        }
        return "";
    }

    public static void addCourse(String name, String section, String semester, String description, String templateName, String filename, int type){
        CourseService.getInstance().addCourse(name,section,semester,description,templateName,filename,type);
    }
}
