package controller;

import model.*;
import service.*;
import utils.Config;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.lang.Math;

public class MainFrameController {
    public static DefaultMutableTreeNode initBreakdownTree(DefaultMutableTreeNode rootNode, List<GradingRule> gradingRules) {
        for (int i = 0; i < gradingRules.size(); i++) {
            String name = gradingRules.get(i).getName();
            String proportion = (int) (gradingRules.get(i).getProportion() * 100) + "%";
            String nodeText = "";
            if (gradingRules.get(i).getChildren() != null && gradingRules.get(i).getChildren().size() != 0) {
                // not leaf node
                nodeText = name + " - " + proportion;
            } else {
                // leaf node
                String fullScore = String.valueOf((int) gradingRules.get(i).getFullScore());
                nodeText = name + " - " + proportion + " - " + fullScore;
            }
            DefaultMutableTreeNode child = new DefaultMutableTreeNode(nodeText);
            rootNode.add(child);

            // add this child's child
            if (gradingRules.get(i).getChildren() != null) {
                if (gradingRules.get(i).getChildren().size() != 0) {
                    initBreakdownTree(child, gradingRules.get(i).getChildren());
                }
            }
        }
        return rootNode;
    }

    public static List<GradingRule> getGradeRuleOfDepth0(List<GradingRule> gradingRules) {
        List<GradingRule> grs = new ArrayList<>();
        for (GradingRule gr : gradingRules) {
            if (gr.getParentID() == null || gr.getParentID().isEmpty()) grs.add(gr);
        }
        return grs;
    }

    public static Course getCourseByID(String courseID) {
        return CourseService.getInstance().getCourse(courseID);
    }

    public static List<GradingRule> getAllGradingRule(Course course) {
        return new ArrayList<>(course.getBreakdown().getGradingRules().values());
    }

    public static Student getStudent(String buid, String courseId) {
        return StudentService.getInstance().getStudent(buid, courseId);
    }

    public static GradingRule getGradingRuleByID(String courseID, String GradingRuleID) {
        return GradingRuleService.getInstance().getGradingRuleByID(courseID, GradingRuleID);
    }

    public static GradingRule getGradingRuleByNameAndCourse(String name, Course course) {
        Breakdown breakdown = course.getBreakdown();
        List<GradingRule> gradingRuleList = new ArrayList<>(breakdown.getGradingRules().values());
        return findGradingRule(name,gradingRuleList);
    }

    private static GradingRule findGradingRule(String name, List<GradingRule> grs){
        for (GradingRule gr : grs) {
            String grName = gr.getName();
            if (grName.equals(name)) return gr;
            else if(gr.getChildren()!=null && gr.getChildren().size()!=0){
                GradingRule tmp = findGradingRule(name,gr.getChildren());
                if(tmp==null) continue;
                else return tmp;
            }
        }
        return null;
    }

    public static GradingRule getGradingRuleByIDAndCourse(String ruleID, Course course){
        Breakdown breakdown = course.getBreakdown();
        List<GradingRule> gradingRuleList = new ArrayList<>(breakdown.getGradingRules().values());
        return findGradingRuleID(ruleID,gradingRuleList);
    }

    private static GradingRule findGradingRuleID(String ruleID, List<GradingRule> grs){
        for (GradingRule gr : grs) {
            String grID = gr.getId();
            if (grID.equals(ruleID)) return gr;
            else if(gr.getChildren()!=null && gr.getChildren().size()!=0){
                GradingRule tmp = findGradingRuleID(ruleID,gr.getChildren());
                if(tmp==null) continue;
                else return tmp;
            }
        }
        return null;
    }

    public static int freezeStudent(String buid, String courseId) {
        return StudentService.getInstance().freezeStudent(buid, courseId);
    }

    public static int deleteStudent(String buid, String courseId) {
        return StudentService.getInstance().deleteStudent(buid, courseId);
    }

    public static DefaultListModel refreshLM(ListModel lm, Map<String, double[]> letterRule) {
        DefaultListModel<String> dlm = new DefaultListModel<>();
        for (int i = 0; i < lm.getSize(); i++) {
            String item = lm.getElementAt(i).toString();
            String[] ss = item.split("  ");
            String letterName = ss[0];

            String Bound = ss[1].replace(" ", "").replace("%", "");
            String[] bounds = Bound.split("-");

            double[] bound = letterRule.get(letterName);
            // if cannot find the letterRule
            if (bound == null) {
                bound = new double[]{0, 0};
            }

            String lowerBound = Integer.toString((int) bound[0]);
            String upperBound = Integer.toString((int) bound[1]);

            String realItem = letterName + "  " + lowerBound + "% - " + upperBound + "%";
            dlm.addElement(realItem);
        }
        return dlm;
    }

    public static boolean isLetterRuleValid(Map<String, double[]> letterRuleMap, String letterName, double lowerBound, double upperBound) {
        if (lowerBound > upperBound) return false;
        for (Map.Entry<String, double[]> entry : letterRuleMap.entrySet()) {
            if (entry.getKey().equals(letterName)) continue;
            double[] bounds = entry.getValue();
            if (isOverlap(lowerBound, bounds[0], upperBound, bounds[1])) return false;
        }
        return true;
    }

    public static boolean isOverlap(double start0, double start1, double end0, double end1) {
        if (Math.max(start0, start1) < Math.min(end0, end1)) return true;
        else return false;
    }

    public static int editLetterRule(String courseId, String letter, double lower, double upper) {
        return BreakdownService.getInstance().editLetterRule(courseId, letter, lower, upper);
    }

    public static int updateGradingRule(String courseID, String gradingRuleId, String name, double fullScore, double proportion) {
        return GradingRuleService.getInstance().editGradingRule(courseID, gradingRuleId, name, fullScore, proportion);
    }

    public static DefaultTableModel initTableData(DefaultTableModel dtm, Course course){
        Map<String, GradingRule> gradingRuleMap = course.getBreakdown().getGradingRules(); // GradingRuleID, GradingRule
        Map<String, Student> StudentMap = course.getStudents(); // BUID, Student
        List<GradingRule> gradingRuleList = new ArrayList<>(gradingRuleMap.values());

        // sort gradingRuleList
        gradingRuleList = sortGradingRuleList(gradingRuleList);

        // remove all rows
        dtm.setRowCount(0);
        dtm.setColumnCount(0);

        // init headers, add columns
        dtm.addColumn("BUID");
        dtm.addColumn("Name");
        for(GradingRule gradingRule : gradingRuleList){
            dtm.addColumn(gradingRule.getName());
        }
        dtm.addColumn(Config.BONUS);
        dtm.addColumn(Config.FINALGRADEPERCENTAGE);
        dtm.addColumn(Config.FINALGRADELETTER);

        // add rows
        for(Student stu : StudentMap.values()){
            String[] row = new String[gradingRuleList.size()+5]; // BUID, name, [Grades], Bonus, Final percentage, Final letter

            // stu info
            row[0] = stu.getBuid();
            row[1] = stu.getName().getFullName();

            // grades
            Map<String, Grade> gradeMap = stu.getGrades(); // ruleID, Grade
            for(int i=2; i<gradingRuleList.size()+2; i++){
                Grade grade = gradeMap.get(gradingRuleList.get(i-2).getId());
                if(grade == null) continue;
                double percentage = grade.getPercentage();
                percentage = (double) Math.round(percentage * 10000)/100;
                String item = percentage+"%";
                row[i] = item;
            }

            // bonus
            row[row.length-3] = String.valueOf((int)stu.getBonus());

            try {
                double finalPercentage = gradeMap.get("final").getPercentage();
                String letterGrade = ((FinalGrade) gradeMap.get("final")).getLetterGrade();
                row[row.length - 2] = (int) (finalPercentage * 100) + "%";
                row[row.length - 1] = letterGrade;
            }catch (Exception e){
                row[row.length - 2] = "0%";
                row[row.length - 1] = "N/A";
            }

            // add row
            dtm.addRow(row);
        }
        return dtm;
    }

    public static List<GradingRule> sortGradingRuleList(List<GradingRule> gradingRuleList){
//        List<GradingRule> gradingRuleDepth0 = getGradeRuleOfDepth0(gradingRuleList);
        List<GradingRule> results = new ArrayList<>();
        for(GradingRule gradingRule : gradingRuleList) {
            results.add(gradingRule);
            if(gradingRule.getChildren() != null && gradingRule.getChildren().size() != 0) {
                results.addAll(sortGradingRuleList(gradingRule.getChildren()));
            }
        }
        return results;
    }

    public static int updateRowScore(String courseId, String buid, Map<String, Double> scores){
        return ScoreService.getInstance().updateRowScore(courseId, buid, scores);
    }

    public static int calculateScores(String courseId){
        return ScoreService.getInstance().calculateScores(courseId);
    }
}
