package service;

import db.BreakdownDAO;
import db.LetterRuleDAO;
import model.Breakdown;
import model.GradingRule;
import utils.ErrCode;

import java.util.*;

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

    public boolean checkLetterRule(Map<String, double[]> letterRule) {
        Map<String, double[]> validLetterRule = new HashMap<>();
        for(Map.Entry<String, double[]> lr : letterRule.entrySet()) {
            if(lr.getValue()[0] != 0.0 || lr.getValue()[1] != 0.0) {
                validLetterRule.put(lr.getKey(), lr.getValue());
            }
        }

        List<Map.Entry<String, double[]>> list = new ArrayList<Map.Entry<String, double[]>>(validLetterRule.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, double[]>>() {
            @Override
            public int compare(Map.Entry<String, double[]> o1, Map.Entry<String, double[]> o2) {
                char c1 = o1.getKey().charAt(0);
                char c2 = o2.getKey().charAt(0);
                if (c1 == c2) {
                    if(o1.getKey().length() == o2.getKey().length()) {
                        return o1.getKey().contains("+") ? -1 : 1;
                    }
                    else if(o1.getKey().length() > o2.getKey().length()) {
                            return o1.getKey().contains("+") ? -1 : 1;
                    }
                    else {
                        return o2.getKey().contains("+") ? -1 : 1;
                    }
                }
                else {
                    return c1 > c2 ? 1 : -1;
                }
            }
        });

        for(int i = 0; i < list.size(); i++) {
            if(i == 0) {
                if(list.get(i).getValue()[0] != 1.0) {
                    return false;
                }
            }
            else {
                if(list.get(i).getValue()[0] != list.get(i-1).getValue()[1]) {
                    return false;
                }


            }
            if(i == list.size() - 1) {
                if(list.get(i).getValue()[1] != 0.0) {
                    return false;
                }
            }
        }
        return true;
    }

    public int editLetterRule(String courseId, String letter, double lower, double upper) {

        return LetterRuleDAO.getInstance().editBreakdownLetterRule(courseId, letter, lower, upper);
    }

    public int addLetterRule(String courseId, String letter, double lower, double upper) {
//        return 0;
        return LetterRuleDAO.getInstance().addBreakdownLetterRule(courseId, letter, lower, upper);
    }

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
//        Map<String, GradingRule> gradingRules = new HashMap<>();
//        GradingRule homework = new GradingRule("1", "", "Homework", 0.0, 0.3);
//        GradingRule Assignment = new GradingRule("2", "", "Assignment", 0.0, 0.6);
//        GradingRule Exam = new GradingRule("3", "", "Exam", 0.0, 0.0);
//        GradingRule Project = new GradingRule("4", "", "Project", 0.0, 0.1);
//
//        homework.getChildren().add(new GradingRule("5", "1", "Homework1", 0.0, 0.5));
//        homework.getChildren().add(new GradingRule("6", "1", "Homework2", 0.0, 0.5));
//
//        Assignment.getChildren().add(new GradingRule("7", "2", "Assignment1", 0.0, 1.0));
//
//        Exam.getChildren().add(new GradingRule("8", "3", "Midterm", 0.0, 0.6));
//        Exam.getChildren().add(new GradingRule("9", "3", "Final", 0.0, 0.4));
//        Exam.getChildren().get(0).getChildren().add(new GradingRule("10", "8", "Written", 0.0, 0.6));
//        Exam.getChildren().get(0).getChildren().add(new GradingRule("12", "8", "Code", 0.0, 0.4));
//
//        gradingRules.put("1", homework);
//        gradingRules.put("2", Assignment);
//        gradingRules.put("3", Exam);
//        gradingRules.put("4", Project);
//
//        Breakdown breakdown = new Breakdown();
//        breakdown.setGradingRules(gradingRules);

        Map<String, double[]> letterRule = new HashMap<>();
        double[] letterPercent1 = {1.0, 0.9};
        double[] letterPercent2 = {0.9, 0.85};
        double[] letterPercent3 = {0.85, 0.8};
        double[] letterPercent4 = {0.8, 0.7};
        double[] letterPercent5 = {0.7, 0.6};
        double[] letterPercent6 = {0.0, 0.0};
        double[] letterPercent7 = {0.6, 0.0};
        double[] letterPercent8 = {0.0, 0.0};
        double[] letterPercent9 = {0.0, 0.0};
        double[] letterPercent0 = {0.0, 0.0};
        letterRule.put("A", letterPercent1);
        letterRule.put("A-", letterPercent2);
        letterRule.put("B+", letterPercent3);
        letterRule.put("B", letterPercent4);
        letterRule.put("B-", letterPercent5);
        letterRule.put("C+", letterPercent6);
        letterRule.put("C", letterPercent7);
        letterRule.put("C-", letterPercent8);
        letterRule.put("D", letterPercent9);
        letterRule.put("F", letterPercent0);


        boolean res = BreakdownService.getInstance().checkLetterRule(letterRule);
        System.out.println(res);
    }


}
