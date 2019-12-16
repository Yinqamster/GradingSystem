package model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Qi Yin
 */
public class GradingRule {
    private String id;
    private String parentID;
    //the highest level of GradingRule (eg: HW, Exam)
    private List<GradingRule> children;
    //not used
    private int depth; // example: 0 - Homework, 1 - Midterm, 2 - Written
    private String name;
    private double fullScore;
    private double proportion;

    public GradingRule(){
        id = "";
        parentID = "";
        children = new ArrayList<>();
        depth = -1;
        name = "";
        fullScore = 0.0;
        proportion = 0.0;
    }

    public void setChildrenID(List<GradingRule> children) {
        this.children = children;
    }

    public GradingRule(String parentID, int depth, String name, double fullScore, double proportion) {
        this();
        this.parentID = parentID;
        this.depth = depth;
        this.name = name;
        this.fullScore = fullScore;
        this.proportion = proportion;
        this.id = UUID.randomUUID().toString();
    }

    public GradingRule(String ID, String parentID, String name, double fullScore, double proportion) {
        this();
        this.parentID = parentID;
        this.id = ID;
        this.name = name;
        this.fullScore = fullScore;
        this.proportion = proportion;
    }

    public GradingRule(String parentID, String name, double fullScore, double proportion) {
        this();
        this.parentID = parentID;
        this.name = name;
        this.fullScore = fullScore;
        this.proportion = proportion;
        this.id = UUID.randomUUID().toString();
    }

    public GradingRule(String ID, String parentID, String name, double proportion) {
        this();
        this.parentID = parentID;
        this.id = ID;
        this.name = name;
        this.fullScore = 0.0;
        this.proportion = proportion;
    }

    public GradingRule(String parentID, String name, double proportion) {
        this();
        this.parentID = parentID;
        this.name = name;
        this.fullScore = 0.0;
        this.proportion = proportion;
        this.id = UUID.randomUUID().toString();
    }

    public GradingRule(String name, double proportion) {
        this();
        this.name = name;
        this.fullScore = 0.0;
        this.proportion = proportion;
        this.id = UUID.randomUUID().toString();
    }

    public GradingRule(String name, double fullScore, double proportion) {
        this();
        this.name = name;
        this.fullScore = fullScore;
        this.proportion = proportion;
        this.id = UUID.randomUUID().toString();
    }

    public GradingRule(String ID, String parentID, String name, double proportion, List<GradingRule> children) {
        this(ID, parentID, name, proportion);
        this.children = children;
    }

    public GradingRule(String ID, String parentID, String name, double fullScore, double proportion, List<GradingRule> children) {
        this(ID, parentID, name,fullScore, proportion);
        this.children = children;
    }

    public GradingRule(String parentID, String name, double proportion, List<GradingRule> children) {
        this(parentID, name, proportion);
        this.children = children;
        this.id = UUID.randomUUID().toString();
    }

    public GradingRule(String parentID, String name, double fullScore, double proportion, List<GradingRule> children) {
        this(parentID, name,fullScore, proportion);
        this.children = children;
        this.id = UUID.randomUUID().toString();
    }

    public GradingRule(String name, double proportion, List<GradingRule> children) {
        this(name, proportion);
        this.children = children;
        this.id = UUID.randomUUID().toString();
    }

    public GradingRule(String name, double fullScore, double proportion, List<GradingRule> children) {
        this(name,fullScore, proportion);
        this.children = children;
        this.id = UUID.randomUUID().toString();
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
