package model;

/**
 * @author Qi Yin
 */
public class FinalGrade extends Grade {
    private String letterGrade;

    public FinalGrade() {
        super();
        letterGrade = "";
    }

    public FinalGrade(double percentage, String letterGrade){
        this();
        setPercentage(percentage);
        this.letterGrade = letterGrade;
    }

    public FinalGrade(String ruleName, double absolute, double percentage,
                      double deduction, String comment, String letterGrade) {
        super(ruleName, absolute, percentage, deduction, comment);
        this.letterGrade = letterGrade;
    }

    public String getLetterGrade() {
        return letterGrade;
    }

    public void setLetterGrade(String letterGrade) {
        this.letterGrade = letterGrade;
    }
}
