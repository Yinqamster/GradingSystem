package db;

import model.*;

import java.sql.SQLException;
import java.util.*;

public class DBtest {

    GradingRule HW;
    GradingRule EXAM;
    GradingRule FINAL;
    GradingRule TICTACTOE;
    GradingRule TICTACTOEII;
    GradingRule MID;
    GradingRule WRITTEN;
    GradingRule PRACTICE;
    List<GradingRule> HWList = new ArrayList<>();
    List<GradingRule> EXAMList = new ArrayList<>();
    List<GradingRule> FINALLIST = new ArrayList<>();
    List<GradingRule> TICTACTOEList = new ArrayList<>();
    List<GradingRule> TICTACTOEIIList = new ArrayList<>();
    List<GradingRule> MIDList = new ArrayList<>();
    List<GradingRule> WRITTENList = new ArrayList<>();
    List<GradingRule> PRACTICEList = new ArrayList<>();
    String breakdownId = "FALLCS591A1Breakdown";

    Breakdown breakdown;

    public static void main(String[] args) throws SQLException {
        DBtest dBtest = new DBtest();
        dBtest.courseDAOTest();
    }

    public void courseDAOTest() {
        Course course = new Course("CS591P1", "Fall", "GOOD CLASS");
        CourseDAO.getInstance().updateCourse(course);
    }

    public void breakdownDAOTest() {
        Map<String, GradingRule> gradingRuleMap = new HashMap<>();
        gradingRuleMap.put(HW.getId(), HW);
        gradingRuleMap.put(EXAM.getId(), EXAM);
        Map<String, double[]> letterMap = new HashMap<>();
        letterMap.put("A", new double[]{90, 100});
        letterMap.put("B", new double[]{80, 89});
        letterMap.put("C", new double[]{60, 79});
        letterMap.put("F", new double[]{0l, 59});
        breakdown = new Breakdown(gradingRuleMap, letterMap, breakdownId);
        BreakdownDAO.getInstance().updateBreakdown(breakdown);
        BreakdownDAO.getInstance().deleteBreakdown(breakdown.getBreakdownID());
    }

    public void gradingRuleDAOTest() {
//        GradingRule HW;
//        GradingRule EXAM;
//        GradingRule FINAL;
//        GradingRule TICTACTOE;
//        GradingRule TICTACTOEII;
//        GradingRule MID;
//        GradingRule WRITTEN;
//        GradingRule PRACTICE;
//        List<GradingRule> HWList = new ArrayList<>();
//        List<GradingRule> EXAMList = new ArrayList<>();
//        List<GradingRule> FINALLIST = new ArrayList<>();
//        List<GradingRule> TICTACTOEList = new ArrayList<>();
//        List<GradingRule> TICTACTOEIIList = new ArrayList<>();
//        List<GradingRule> MIDList = new ArrayList<>();
//        List<GradingRule> WRITTENList = new ArrayList<>();
//        List<GradingRule> PRACTICEList = new ArrayList<>();
//        String breakdownId = "FALLCS591A1Breakdown";

        PRACTICE = new GradingRule("practice", 100, 0.2, PRACTICEList);
        PRACTICE.setId(breakdownId + PRACTICE.getName());
        WRITTEN = new GradingRule("written", 100, 0.2, WRITTENList);
        WRITTEN.setId(breakdownId + WRITTEN.getName());
        MIDList.add(PRACTICE);
        MIDList.add(WRITTEN);

        MID = new GradingRule("midterm", 0.4, MIDList);
        MID.setId(breakdownId + MID.getName());
        FINAL = new GradingRule("final", 100, 0.2, FINALLIST);
        FINAL.setId(breakdownId + FINAL.getName());

        EXAMList.add(MID);
        EXAMList.add(FINAL);
        EXAM = new GradingRule("", "exam", 0.6, EXAMList);
        EXAM.setId(breakdownId + EXAM.getName());

        TICTACTOE = new GradingRule("tictactoe", 150, 0.2, TICTACTOEList);
        TICTACTOE.setId(breakdownId + TICTACTOE.getName());
        TICTACTOEII = new GradingRule("tictactoeii", 100, 0.2, TICTACTOEIIList);
        TICTACTOEII.setId(breakdownId + TICTACTOEII.getName());
        HWList.add(TICTACTOE);
        HWList.add(TICTACTOEII);

        HW = new GradingRule("", "hw", 0.4, HWList);
        HW.setId(breakdownId + HW.getName());

        PRACTICE.setParentID(MID.getId());
        WRITTEN.setParentID(MID.getId());
        MID.setParentID(EXAM.getId());
        FINAL.setParentID(EXAM.getId());
        TICTACTOE.setParentID(HW.getId());
        TICTACTOEII.setParentID(HW.getId());

        GradingRuleDAO.getInstance().updateBreakdownGradingRule(EXAM, "FALLCS591A1Breakdown");
        GradingRuleDAO.getInstance().updateBreakdownGradingRule(HW, "FALLCS591A1Breakdown");

        GradingRule gradingRule = GradingRuleDAO.getInstance().getGradingRule("examFALLCS591A1");
        GradingRuleDAO.getInstance().deleteGradingRule("examFALLCS591A1");
        System.out.println();
    }

    public void studentDAOTest() {
        int code = StudentDAO.getInstance().addStudent("Jerry", "", "Tom", "U28384838", "graduate", "", "CS591P1EEE");
        System.out.println(code);
        Student student = StudentDAO.getInstance().getStudent("U28384838", "CS591P1");
        System.out.println(student);
        student.setComment("Good Boy");
        code = StudentDAO.getInstance().updateStudent(student);
        student = StudentDAO.getInstance().getStudent("U28384838", "CS591P1");
        System.out.println(student);
        StudentDAO.getInstance().freezeStudent("U28384838", "CS591P1EEE");
        StudentDAO.getInstance().deleteStudent("U28384838", "CS591P1EEE");
    }
}
