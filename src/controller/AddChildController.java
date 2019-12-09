package controller;

import model.Course;
import model.GradingRule;
import service.GradingRuleService;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class AddChildController {
    public static GradingRule getGradingRuleByID(String courseID, String GradingRuleID){
        return GradingRuleService.getInstance().getGradingRuleByID(courseID, GradingRuleID);
    }

    public static boolean isRuleNameUnique(String ruleName, Course course){
        List<GradingRule> ruleList = new ArrayList<>(course.getBreakdown().getGradingRules().values());
        for(GradingRule gr : ruleList){
            if(gr.getName().equals(ruleName)) return false;
        }
        return true;
    }

    public static int addGradingRule(String courseID, String name, double fullScore, double proportion, String parentId, int depth){
        return GradingRuleService.getInstance().addGradingRule(courseID, name, fullScore, proportion, parentId, depth);
    }
}
