package model;

public class GradingRule {
    private int id;
    private int parentID;
    private int depth;
    private String name;
    private double fullScore;
    private double proportion;

    public GradingRule(){

    }

    public GradingRule(int parentID, String name, double fullScore, double proportion) {
        this.parentID = parentID;
        this.name = name;
        this.fullScore = fullScore;
        this.proportion = proportion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentID() {
        return parentID;
    }

    public void setParentID(int parentID) {
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
}
