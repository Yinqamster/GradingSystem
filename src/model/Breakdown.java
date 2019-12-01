package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Breakdown {
    private List<GradingRule> gradingRules;
    private Map<String, double[]> letterRule;
    private String breakdownID;

    public Breakdown(){
        gradingRules = new ArrayList<>();
        letterRule = new HashMap<>();
    }

    public Breakdown(List<GradingRule> gradingRules, Map<String, double[]> letterRule, String breakdownID) {
        this.gradingRules = gradingRules;
        this.letterRule = letterRule;
        this.breakdownID = breakdownID;
    }

    public List<GradingRule> getGradingRules() {
        return gradingRules;
    }

    public void setGradingRules(List<GradingRule> gradingRules) {
        this.gradingRules = gradingRules;
    }

    public Map<String, double[]> getLetterRule() {
        return letterRule;
    }

    public void setLetterRule(Map<String, double[]> letterRule) {
        this.letterRule = letterRule;
    }

    public String getBreakdownID() {
        return breakdownID;
    }

    public void setBreakdownID(String breakdownID) {
        this.breakdownID = breakdownID;
    }
}
