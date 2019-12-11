package service;

import db.BreakdownDAO;
import db.LetterRuleDAO;
import model.Breakdown;
import model.GradingRule;
import utils.ErrCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BreakdownService {
    private static BreakdownService instance = new BreakdownService();

    private BreakdownService(){

    }
    public static BreakdownService getInstance() {
//        if (instance == null) {
//            instance = new BreakdownService();
//        }
        return instance;
    }


//    public int editLetterRule(String courseId, Map<String, double[]> letterRule) {
//        return LetterRuleDAO.getInstance().updateLetterMap(letterRule, courseId);
//    }

    // TODO for test-----------------
    public int editLetterRule(String courseId, String letter, double lower, double upper) {

        return LetterRuleDAO.getInstance().editBreakdownLetterRule(courseId, letter, lower, upper);
    }

    public int addLetterRule(String courseId, String letter, double lower, double upper) {
//        return 0;
        return LetterRuleDAO.getInstance().addBreakdownLetterRule(courseId, letter, lower, upper);
    }
    // TODO --------------------------------------------------

    public Breakdown getBreakdownByID(String breakdownID) {
        return BreakdownDAO.getInstance().getBreakdown(breakdownID);
    }

    //only for test
//    public int checkBreakdown(Breakdown breakdown) {
    public int checkBreakdown(String breakdownID) {
        Breakdown breakdown = getBreakdownByID(breakdownID);
        if(breakdown == null) {
            return ErrCode.BREAKDOWNNOTEXIST.getCode();
        }
        if(breakdown.getGradingRules() == null || breakdown.getGradingRules().size() == 0) {
            return ErrCode.GRADINGRULEEMPTY.getCode();
        }
        Map<String, GradingRule> gradingRules = breakdown.getGradingRules();
        double sum = 0.0;
        boolean res = true;
        for(GradingRule gradingRule : gradingRules.values()) {
            sum += 100 * gradingRule.getProportion();
        }
        if(sum != 100.0) {
            return ErrCode.SUMWRONG.getCode();
        }

        for(GradingRule gradingRule : gradingRules.values()) {
            res = checkGradingRule(gradingRule);
            if(!res) return ErrCode.SUMWRONG.getCode();
        }


        return ErrCode.OK.getCode();
    }

    private boolean checkGradingRule(GradingRule gradingRule) {
        if(gradingRule.getChildren() != null && gradingRule.getChildren().size() != 0) {
            double sum = 0.0;
            for(GradingRule gr : gradingRule.getChildren()) {
                sum += 100 * gr.getProportion();
            }
            if(sum != 100.0){
                return false;
            }
            for(GradingRule gr : gradingRule.getChildren()) {
                if(!checkGradingRule(gr)) return false;
            }
        }
        return true;
    }

    public static void main(String args[]) {
        Map<String, GradingRule> gradingRules = new HashMap<>();
        GradingRule homework = new GradingRule("1", "", "Homework", 0.0, 0.3);
        GradingRule Assignment = new GradingRule("2", "", "Assignment", 0.0, 0.6);
        GradingRule Exam = new GradingRule("3", "", "Exam", 0.0, 0.0);
        GradingRule Project = new GradingRule("4", "", "Project", 0.0, 0.1);

        homework.getChildren().add(new GradingRule("5", "1", "Homework1", 0.0, 0.5));
        homework.getChildren().add(new GradingRule("6", "1", "Homework2", 0.0, 0.5));

        Assignment.getChildren().add(new GradingRule("7", "2", "Assignment1", 0.0, 1.0));

        Exam.getChildren().add(new GradingRule("8", "3", "Midterm", 0.0, 0.6));
        Exam.getChildren().add(new GradingRule("9", "3", "Final", 0.0, 0.4));
        Exam.getChildren().get(0).getChildren().add(new GradingRule("10", "8", "Written", 0.0, 0.6));
        Exam.getChildren().get(0).getChildren().add(new GradingRule("12", "8", "Code", 0.0, 0.4));

        gradingRules.put("1", homework);
        gradingRules.put("2", Assignment);
        gradingRules.put("3", Exam);
        gradingRules.put("4", Project);

        Breakdown breakdown = new Breakdown();
        breakdown.setGradingRules(gradingRules);

//        int code = BreakdownService.getInstance().checkBreakdown(breakdown);
//        System.out.println(code);


//        double a = 0.6;
//        double b = 0.3;
//        System.out.println(a + b);
    }


}
