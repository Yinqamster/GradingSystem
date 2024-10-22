package model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Qi Yin
 */
public class Breakdown {
    //gradingRule id, gradingRule
    private Map<String, GradingRule> gradingRules;
    //letter, [lower, upper]
    private Map<String, double[]> letterRule;
    private String breakdownID;

    public Breakdown(){
        gradingRules = new HashMap<>();
        letterRule = new HashMap<>();
        breakdownID = "";
        double[] letterPercent = {0.0, 0.0};
        letterRule.put("A", letterPercent);
        letterRule.put("A-", letterPercent);
        letterRule.put("B+", letterPercent);
        letterRule.put("B", letterPercent);
        letterRule.put("B-", letterPercent);
        letterRule.put("C+", letterPercent);
        letterRule.put("C", letterPercent);
        letterRule.put("C-", letterPercent);
        letterRule.put("D", letterPercent);
        letterRule.put("F", letterPercent);
    }

    public Breakdown(Map<String, GradingRule> gradingRules,
                     Map<String, double[]> letterRule, String breakdownID) {
        this();
        this.gradingRules = gradingRules;
        this.letterRule = letterRule;
        this.breakdownID = breakdownID;
    }

    public Map<String, GradingRule> getGradingRules() {
        return gradingRules;
    }

    public void setGradingRules(Map<String, GradingRule> gradingRules) {
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
