package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Breakdown {
    private List<GradingRule> gradingRules;
    private Map<String, double[]> letterRule;

    public Breakdown(){
        gradingRules = new ArrayList<>();
        letterRule = new HashMap<>();
    }

    public Breakdown(List<GradingRule> gradingRules, Map<String, double[]> letterRule) {
        this.gradingRules = gradingRules;
        this.letterRule = letterRule;
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
}
