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


    public int editLetterRule(String courseId, Map<String, double[]> letterRule) {
        return LetterRuleDAO.getInstance().updateBreakdownLetterMap(letterRule, courseId);
    }

    public int editLetterRule(String courseId, String letter, double lower, double upper) {


        return ErrCode.OK.getCode();
    }

    public Breakdown getBreakdownByID(String breakdownID) {
        return BreakdownDAO.getInstance().getBreakdown(breakdownID);
    }


}
