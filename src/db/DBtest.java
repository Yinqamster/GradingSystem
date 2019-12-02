package db;

import model.*;

import java.sql.SQLException;
import java.util.*;

public class DBtest {
    public static void main(String[] args) throws SQLException {
        GradingRule HW = new GradingRule(UUID.randomUUID().toString().substring(0,9), "", "HW", 0.8);
        GradingRule EXAM = new GradingRule(UUID.randomUUID().toString().substring(0,9), "", "HW", 100,0.2);
        GradingRule HW1 = new GradingRule(UUID.randomUUID().toString().substring(0,9), HW.getId(), "HW1", 100, 0.4);
        GradingRule HW2 = new GradingRule(UUID.randomUUID().toString().substring(0,9), HW.getId(), "HW2", 100, 0.4);
        List<GradingRule> gradingRuleList = new ArrayList<>();
        gradingRuleList.add(HW);
        gradingRuleList.add(EXAM);
        gradingRuleList.add(HW1);
        gradingRuleList.add(HW2);

        Map<String, double[]> letterMap = new HashMap<>();
        double[] scoreSlot1 = {90, 100};
        double[] scoreSlot2 = {60, 89};
        double[] scoreSlot3 = {0, 59};
        letterMap.put("A",scoreSlot1);
        letterMap.put("B", scoreSlot2);
        letterMap.put("F", scoreSlot3);

        Breakdown breakdown = new Breakdown(gradingRuleList, letterMap, UUID.randomUUID().toString().substring(0,9));

        Course course = new Course("CS591P1", "FALL", "OOD");

        Name name = new Name("James D.R Keven");

        Grade grade1 = new Grade(EXAM.getId(), EXAM.getName(), 100, 1, 0);
        Grade grade2 = new Grade(HW1.getId(), HW1.getName(), 100, 1, 0, "Well Done");
        Grade grade3 = new Grade(HW2.getId(), HW2.getName(), 100, 1, 0);
        List<Grade> gradeList = new ArrayList<>();
        gradeList.add(grade1);
        gradeList.add(grade2);
        gradeList.add(grade3);

        Student s1 = new GraduateStudent(name, "U84759384", 0, 0, gradeList);

        StudentDAO.getInstance().updateStudent(s1);

        Student student = StudentDAO.getInstance().getStudent("U84759384");
        System.out.println(student);
    }
}
