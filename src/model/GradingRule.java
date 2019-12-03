package model;

import java.util.List;

public class GradingRule {
    private String id;
    private String parentID;
    private List<GradingRule> children;
    private int depth; // example: 0 - Homework, 1 - Midterm, 2 - Written
    private String name;
    private double fullScore;
    private double proportion;

    public GradingRule(){

    }

    public GradingRule(String ID, String parentID, String name, double fullScore, double proportion) {
        this.parentID = parentID;
        this.id = ID;
        this.name = name;
        this.fullScore = fullScore;
        this.proportion = proportion;
    }

    public GradingRule(String ID, String parentID, String name, double proportion) {
        this.parentID = parentID;
        this.id = ID;
        this.name = name;
        this.fullScore = 0.0;
        this.proportion = proportion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentID() {
        return parentID;
    }

    public void setParentID(String parentID) {
        this.parentID = parentID;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getFullScore() {
        return fullScore;
    }

    public void setFullScore(double fullScore) {
        this.fullScore = fullScore;
    }

    public double getProportion() {
        return proportion;
    }

    public void setProportion(double proportion) {
        this.proportion = proportion;
    }

    public List<GradingRule> getChildren() {
        return children;
    }

    public void setChildren(List<GradingRule> children) {
        this.children = children;
    }
}
