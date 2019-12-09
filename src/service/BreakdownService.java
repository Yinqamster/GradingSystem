package service;

import db.BreakdownDAO;
import db.LetterRuleDAO;
import model.Breakdown;
import utils.ErrCode;

import java.util.HashMap;
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


}
