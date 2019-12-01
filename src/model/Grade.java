package model;

public class Grade {
    private String ruleName;
    private double absolute;
    private double percentage;
    private double deduction;
    private String comment;

    public Grade(){

    }

    public Grade(String ruleName, double absolute, double percentage, double deduction, String comment) {
        this.ruleName = ruleName;
        this.absolute = absolute;
        this.percentage = percentage;
        this.deduction = deduction;
        this.comment = comment;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
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
