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
        return GradingRuleDAO.getInstance().updateGradingRule(gradingRule, courseID);
    }

    public static List<GradingRule> getAllCategories(int depth){
        //TODO get the list of GradingRule given depth

        return new ArrayList<>();
    }
}
