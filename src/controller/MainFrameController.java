package controller;

import model.*;
import service.BreakdownService;
import service.CourseService;
import service.GradingRuleService;
import service.StudentService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
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
        for (GradingRule gr : gradingRuleList) {
            if (gr.getName().equals(name)) return gr;
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
        if (Math.max(start0, start1) <= Math.min(end0, end1)) return true;
        else return false;
    }

    public static int editLetterRule(String courseId, String letter, double lower, double upper) {
        return BreakdownService.getInstance().editLetterRule(courseId, letter, lower, upper);
    }

    public static int updateGradingRule(String courseID, String name, double fullScore, double proportion, String parentId, int depth) {
        return GradingRuleService.getInstance().addGradingRule(courseID, name, fullScore, proportion, parentId, depth);
    }

    public static DefaultTableModel initTableData(DefaultTableModel dtm, Course course){
        Map<String, GradingRule> gradingRuleMap = course.getBreakdown().getGradingRules(); // GradingRuleID, GradingRule
        Map<String, Student> StudentMap = course.getStudents(); // BUID, Student
        List<GradingRule> gradingRuleList = new ArrayList<>(gradingRuleMap.values());

        // sort gradingRuleList
        gradingRuleList = sortGradingRuleList(gradingRuleList);

        // remove all rows
        dtm.setRowCount(0);

        // init headers, add columns
        for(GradingRule gradingRule : gradingRuleList){
            dtm.addColumn(gradingRule.getName());
        }
        dtm.addColumn("Bonus");
        dtm.addColumn("Final Grade");

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
                double percentage = grade.getPercentage();
                String item = String.valueOf((int)percentage*100)+"%";
                row[i] = item;
            }

            // bonus
            row[row.length-3] = String.valueOf((int)stu.getBonus());

            // TODO final grade
//            row[row.length-2] =
//            row[row.length-1] =

            // add row
            dtm.addRow(row);
        }
        return dtm;
    }

    public static List<GradingRule> sortGradingRuleList(List<GradingRule> gradingRuleList){
        return new ArrayList<>();
    }
}
