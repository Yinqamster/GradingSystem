package model;

public class Grade {
    private String ruleId;
    private double absolute;
    private double percentage;
    private double deduction;
    private String comment;

    public Grade(){

    }

    public Grade(String ruleId, double absolute, double percentage, double deduction, String comment) {
        this.ruleId = ruleId;
        this.absolute = absolute;
        this.percentage = percentage;
        this.deduction = deduction;
        this.comment = comment;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public double getAbsolute() {
        return absolute;
    }

    public void setAbsolute(double absolute) {
        this.absolute = absolute;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public double getDeduction() {
        return deduction;
    }

    public void setDeduction(double deduction) {
        this.deduction = deduction;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
