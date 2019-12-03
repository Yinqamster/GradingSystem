package service;

import utils.ErrCode;

import java.util.HashMap;
import java.util.Map;

public class BreakdownService {
    private static BreakdownService instance;

    public static BreakdownService getInstance() {
        if (instance == null) {
            instance = new BreakdownService();
        }
        return instance;
    }

    public int editBreakdown(int id, String name, double fullScore, double proportion) {
        return ErrCode.OK.getCode();
    }

    public int editLetterRule(int courseId, Map<String, double[]> letterRule) {
        return ErrCode.OK.getCode();
    }


}
