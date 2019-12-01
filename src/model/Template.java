package model;

import java.util.Map;

public class Template extends Breakdown {
    private String name;

    public Template() {
        super();
    }

    public Template(String name) {
        this();
        this.name = name;
    }

    public Template(String templateID, String name, Map<String, GradingRule> gradingRules, Map<String, double[]> letterRule) {
        super(gradingRules, letterRule, templateID);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
