package db;

import model.*;

import java.sql.SQLException;
import java.util.*;

public class DBtest {
    public static void main(String[] args) throws SQLException {
//        int code = StudentDAO.getInstance().addStudent("Jerry", "", "Tom", "U28384838", "graduate", "", "CS591P1EEE");
//        System.out.println(code);
//        Student student = StudentDAO.getInstance().getStudent("U28384838");
//        System.out.println(student);
//        student.setComment("Good Boy");
//        code = StudentDAO.getInstance().updateStudent(student);
//        student = StudentDAO.getInstance().getStudent("U28384838");
//        System.out.println(student);
//        StudentDAO.getInstance().freezeStudent("U28384838", "CS591P1EEE");
//        StudentDAO.getInstance().deleteStudent("U28384838", "CS591P1EEE");
//        GradingRule gradingRule = GradingRuleDAO.getInstance().getGradingRule("2019FALLCS591fEXAM");
        GradingRuleDAO.getInstance().deleteGradingRule("2019FALLCS591EXAM");
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

        PRACTICE = new GradingRule("practicum", "midtermFALLCS591A1", "practice", 100, 0.2, PRACTICEList);
        WRITTEN = new GradingRule("written", "midtermFALLCS591A1", "written", 100, 0.2, WRITTENList);
        GradingRule temp = new GradingRule("temp", "hwFALLCS591A1", "temp", 100, 0.2, new ArrayList<>());
        MIDList.add(PRACTICE);
        MIDList.add(WRITTEN);

        MID = new GradingRule("midterm", "examFALLCS591A1", "midterm", 0.4, MIDList);
        FINAL = new GradingRule("final", "examFALLCS591A1", "final", 100, 0.2, FINALLIST);

        EXAMList.add(MID);
        EXAMList.add(FINAL);
        EXAM = new GradingRule("exam", "", "exam", 0.6, EXAMList);

        TICTACTOE = new GradingRule("tictactoe", "hwFALLCS591A1", "tictactoe", 150, 0.2, TICTACTOEList);
        TICTACTOEII = new GradingRule("tictactoeii", "hwFALLCS591A1", "tictactoeii", 100, 0.2, TICTACTOEIIList);
        HWList.add(TICTACTOE);
        HWList.add(TICTACTOEII);

        HW = new GradingRule("hw", "", "hw", 0.4, HWList);

        GradingRuleDAO.getInstance().updateGradingRule(EXAM, "FALLCS591A1");
        GradingRuleDAO.getInstance().updateGradingRule(HW, "FALLCS591A1");

        GradingRule gradingRule = GradingRuleDAO.getInstance().getGradingRule("examFALLCS591A1");
        GradingRuleDAO.getInstance().deleteGradingRule("examFALLCS591A1");
        System.out.println();
    }
}
