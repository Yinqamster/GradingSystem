package service;

import model.GradingRule;
import utils.ErrCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GradingRuleService {
    private static GradingRuleService instance;

    public static GradingRuleService getInstance() {
        if (instance == null) {
            instance = new GradingRuleService();
        }
        return instance;
    }

    public int addGradingRule(String name, double fullScore, double proportion, int parentId) {
//        GradingRule gradingRule = new GradingRule(parentId, name, fullScore, proportion);

        //TODO insert grading rule into database
        return ErrCode.OK.getCode();
    }
}
