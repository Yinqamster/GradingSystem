package service;

import db.GradingRuleDAO;
import model.GradingRule;
import utils.ErrCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *@author Qi Yin
 */
public class GradingRuleService {
    private static GradingRuleService instance = new GradingRuleService();

    private GradingRuleService() {

    }
    public static GradingRuleService getInstance() {
//        if (instance == null) {
//            instance = new GradingRuleService();
//        }
        return instance;
    }

    //add a grading rule
    public int addGradingRule(String courseID, String name, double fullScore, double proportion, String parentId, int depth) {
        GradingRule gradingRule = new GradingRule(parentId, depth, name, fullScore, proportion);
        return GradingRuleDAO.getInstance().updateBreakdownGradingRule(gradingRule, courseID);
    }

    //edit a existing grading
    public int editGradingRule(String courseID, String gradingRuleID, String name, double fullScore, double proportion) {
        GradingRule gradingRule = GradingRuleDAO.getInstance().getGradingRule(gradingRuleID);
        gradingRule.setName(name);
        gradingRule.setFullScore(fullScore);
        gradingRule.setProportion(proportion);
        return GradingRuleDAO.getInstance().updateBreakdownGradingRule(gradingRule, courseID);
    }

    public GradingRule getGradingRuleByID(String courseID, String gradingRuleID){
        return GradingRuleDAO.getInstance().getGradingRule(gradingRuleID);
    }

}
