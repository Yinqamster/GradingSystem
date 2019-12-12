package db;

import model.*;
import service.StudentService;

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

    Course course;

    public static void main(String[] args) {
        DBtest dBtest = new DBtest();
        dBtest.courseDAOTest();
//        dBtest.studentDAOTest();
//        dBtest.gradingRuleDAOTest();
//        GradingRule gradingRule = new GradingRule("EXAM", 2, "Bonus Exam", 100, 0.0);
//        GradingRuleDAO.getInstance().updateBreakdownGradingRule(gradingRule, "CS591P1");
    }

    public void courseDAOTest() {
        course = new Course("CS591P1", "Fall", "GOOD CLASS");
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
        letterMap.put("F", new double[]{0, 59});
        breakdown = new Breakdown(gradingRuleMap, letterMap, course.getCourseID());
        BreakdownDAO.getInstance().updateBreakdown(breakdown);
        BreakdownDAO.getInstance().deleteBreakdown(breakdown.getBreakdownID());
    }

    public void gradingRuleDAOTest() {
        PRACTICE = new GradingRule("practice", 100, 0.2, PRACTICEList);
        PRACTICE.setId(course.getCourseID() + PRACTICE.getName());
        WRITTEN = new GradingRule("written", 100, 0.2, WRITTENList);
        WRITTEN.setId(course.getCourseID() + WRITTEN.getName());
        MIDList.add(PRACTICE);
        MIDList.add(WRITTEN);

        MID = new GradingRule("midterm", 0.4, MIDList);
        MID.setId(course.getCourseID() + MID.getName());
        FINAL = new GradingRule("final", 100, 0.2, FINALLIST);
        FINAL.setId(course.getCourseID() + FINAL.getName());

        EXAMList.add(MID);
        EXAMList.add(FINAL);
        EXAM = new GradingRule("", "exam", 0.6, EXAMList);
        EXAM.setId(course.getCourseID() + EXAM.getName());

        TICTACTOE = new GradingRule("tictactoe", 150, 0.2, TICTACTOEList);
        TICTACTOE.setId(course.getCourseID() + TICTACTOE.getName());
        TICTACTOEII = new GradingRule("tictactoeii", 100, 0.2, TICTACTOEIIList);
        TICTACTOEII.setId(course.getCourseID() + TICTACTOEII.getName());
        HWList.add(TICTACTOE);
        HWList.add(TICTACTOEII);

        HW = new GradingRule("", "hw", 0.4, HWList);
        HW.setId(course.getCourseID() + HW.getName());

        PRACTICE.setParentID(MID.getId());
        WRITTEN.setParentID(MID.getId());
        MID.setParentID(EXAM.getId());
        FINAL.setParentID(EXAM.getId());
        TICTACTOE.setParentID(HW.getId());
        TICTACTOEII.setParentID(HW.getId());

        GradingRuleDAO.getInstance().updateBreakdownGradingRule(EXAM, "CS591P1");
        GradingRuleDAO.getInstance().updateBreakdownGradingRule(HW, "CS591P1");

        GradingRule gradingRule = GradingRuleDAO.getInstance().getGradingRule("examFALLCS591A1");
//        GradingRuleDAO.getInstance().deleteGradingRule("examFALLCS591A1");
        System.out.println();
    }

    public void studentDAOTest() {
        int code = StudentDAO.getInstance().addStudent("Jerry", "", "Tom", "U28384838", "graduate", "", "CS591P1");
        System.out.println(code);
        Student student = StudentDAO.getInstance().getStudent("U28384838", "CS591P1");
        System.out.println(student);
        student.setComment("Good Boy");
        code = StudentDAO.getInstance().addStudent("CS591P1", student);
        System.out.println("code: " + code);
        student.setBuid("U28384837");
        code = StudentDAO.getInstance().addStudent("CS591P1", student);
        System.out.println("code: " + code);
        student.setBuid("U28384839");
        code = StudentDAO.getInstance().addStudent("CS591P1", student);
        System.out.println("code: " + code);
        student.setBuid("U28384831");
        code = StudentDAO.getInstance().addStudent("CS591P1", student);
        System.out.println("code: " + code);
        student.setBuid("U28384832");
        code = StudentDAO.getInstance().addStudent("CS591P1", student);
        System.out.println("code: " + code);
        student.setBuid("U28384832");
        code = StudentDAO.getInstance().addStudent("CS591P1", student);
        System.out.println("code: " + code);
        student.setComment("asdf");
        code = StudentDAO.getInstance().addStudent("CS591P2", student);
        System.out.println("code: " + code);
//        student = StudentDAO.getInstance().getStudent("U28384838", "CS591P1");
//        System.out.println(student);
//        StudentDAO.getInstance().freezeStudent("U28384838", "CS591P1EEE");
//        StudentDAO.getInstance().deleteStudent("U28384838", "CS591P1EEE");
    }
}
