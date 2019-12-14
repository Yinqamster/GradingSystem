package service;

import db.GradingRuleDAO;
import model.GradingRule;
import utils.ErrCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public int addGradingRule(String courseID, String name, double fullScore, double proportion, String parentId, int depth) {
        GradingRule gradingRule = new GradingRule(parentId, depth, name, fullScore, proportion);
        return GradingRuleDAO.getInstance().updateBreakdownGradingRule(gradingRule, courseID);
    }

    public int editGradingRule(String courseID, String gradingRuleID, String name, double fullScore, double proportion) {
        GradingRule gradingRule = GradingRuleDAO.getInstance().getGradingRule(gradingRuleID);
        gradingRule.setName(name);
        gradingRule.setFullScore(fullScore);
        gradingRule.setProportion(proportion);
        return GradingRuleDAO.getInstance().updateBreakdownGradingRule(gradingRule, courseID);
    }

    public GradingRule getGradingRuleByID(String courseID, String gradingRuleID){
        // TODO given GradingRuleID and courseID, return GradingRule
        return GradingRuleDAO.getInstance().getGradingRule(gradingRuleID);
    }

}
