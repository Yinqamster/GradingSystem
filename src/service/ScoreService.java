package service;

import controller.MainFrameController;
import model.*;
import db.GradeDAO;
import utils.Config;
import utils.ErrCode;
import java.util.*;
import java.text.DecimalFormat;

public class ScoreService {
    private static ScoreService instance = new ScoreService();
//    public CourseService courseService = CourseService.getInstance();

    private ScoreService(){

    }
    public static ScoreService getInstance() {
//        if (instance == null) {
//            instance = new ScoreService();
//        }
        return instance;
    }

    // only update scores that is not a composite, i.e. generated directly by grading
    public int updateRowScore(String courseId, String buid, Map<String, Double> scores) {
        // get course, breakdown and student
        Course course = CourseService.getInstance().getCourse(courseId);
        if (course == null) {
            return ErrCode.COURSENOTEXIST.getCode();
        }
        Breakdown breakdown = course.getBreakdown();
        if (breakdown == null) {
            return ErrCode.BREAKDOWNNOTEXIST.getCode();
        }
        // Student student = course.getStudents().get(buid);
        Student student = StudentService.getInstance().getStudent(buid, courseId);
        if (student == null) {
            return ErrCode.STUDENTNOTEXIST.getCode();
        }

        // update score
        for (String ruleId : scores.keySet()) {
            // added by Jun Li -----------------------------------
            GradingRule rule = GradingRuleService.getInstance().getGradingRuleByID(courseId,ruleId);
            //----------------------------------------------------

            // only update scores that is not composite
            if (rule.getChildren() == null || rule.getChildren().size() == 0) {
                Grade grade = GradeDAO.getInstance().getGrade(buid, ruleId);
                double absolute = scores.get(ruleId);
                grade.setAbsolute(absolute);
                grade.setPercentage(absolute / rule.getFullScore());
                grade.setDeduction(rule.getFullScore() - absolute);
                // update student grade in DB
                GradeDAO.getInstance().upgradeGrade(ruleId, buid, grade);
            }
        }

        return ErrCode.OK.getCode();
    }

    public int updateGradeComment(String courseId, String buid, String ruleId, String comment) {
        // get course, student and grade
        Course course = CourseService.getInstance().getCourse(courseId);
        if (course == null) {
            return ErrCode.COURSENOTEXIST.getCode();
        }
        Student student = course.getStudents().get(buid);
        if (student == null) {
            return ErrCode.STUDENTNOTEXIST.getCode();
        }
        //Grade grade = student.getGrades().get(ruleId);
        //grade.setComment(comment);
        return GradeDAO.getInstance().updateGrade(ruleId, buid, comment);
    }

    // calculate and update all scores
    public int calculateScores(String courseId) {
        // get course, breakdown and students
        Course course = CourseService.getInstance().getCourse(courseId);
        if (course == null) {
            return ErrCode.COURSENOTEXIST.getCode();
        }
        Breakdown breakdown = course.getBreakdown();
        if (breakdown == null) {
            return ErrCode.BREAKDOWNNOTEXIST.getCode();
        }
        // validate breakdown
        int isBreakdownValid = BreakdownService.getInstance().checkBreakdown(breakdown.getBreakdownID());
        if (isBreakdownValid != ErrCode.OK.getCode()) {
            return isBreakdownValid;
        }
        Map<String, Student> students = course.getStudents();

        // for each student: calculate scores and final grade
        for (String buid : students.keySet()) {
            double finalAbsolute = 0;
            double finalPercentage = 0;
            double finalDeduction = 0;

            // calculate and update each composite score
            for (String ruleId : breakdown.getGradingRules().keySet()) {
                double[] scores = calculateScore(buid, courseId, ruleId);
                GradingRule rule = GradingRuleService.getInstance().getGradingRuleByID(courseId, ruleId);
                double proportion = rule.getProportion();

                finalAbsolute += scores[0] * proportion;
                finalPercentage += scores[1] * proportion;
                finalDeduction += scores[2] * proportion;
            }

            // calculate and update final score
            calculateFinalGrade(courseId, buid, finalAbsolute, finalPercentage, finalDeduction);
        }

        return ErrCode.OK.getCode();
    }

    // calculate and update score for a grading rule
    // return the absolute score, percentage score, deduction score for this rule
    private double[] calculateScore(String buid, String courseId, String ruleId) {
        double[] scores = new double[3];
        GradingRule rule = GradingRuleService.getInstance().getGradingRuleByID(courseId, ruleId);
        Grade grade = GradeDAO.getInstance().getGrade(buid, ruleId);

        // if it is a leaf rule, i.e. no children
        if (rule.getChildren() == null || rule.getChildren().size() == 0) {
            scores[0] = grade.getAbsolute();
            scores[1] = grade.getPercentage();
            scores[2] = grade.getDeduction();
        }
        // if it is a composite rule, i.e. has children
        else {
            double totalAbsolute = 0;
            double totalPercentage = 0;
            double totalDeduction = 0;

            // get its score by adding scores of children
            for (GradingRule child : rule.getChildren()) {
                double[] childScores = calculateScore(buid, courseId, child.getId());
                double childProportion = child.getProportion();
                totalAbsolute += childScores[0] * childProportion;
                totalPercentage += childScores[1] * childProportion;
                totalDeduction += childScores[2] * childProportion;
            }
            scores[0] = totalAbsolute;
            scores[1] = totalPercentage;
            scores[2] = totalDeduction;
            // update grade
            grade.setAbsolute(totalAbsolute);
            grade.setPercentage(totalPercentage);
            grade.setDeduction(totalDeduction);
        }

        // update grade for this student
        GradeDAO.getInstance().upgradeGrade(ruleId, buid, grade);
        return scores;
    }

    // calculate and update the final grade for a student
    private void calculateFinalGrade(String courseId, String buid, double absolute, double percentage, double deduction) {
        // calculate letter grade
        Breakdown breakdown = CourseService.getInstance().getCourse(courseId).getBreakdown();
        Map<String, double[]> letterRule = breakdown.getLetterRule();
        String letterGrade = "";
        double numberGrade = percentage * 100;
        for (String letter : letterRule.keySet()) {
            double[] bounds = letterRule.get(letter);
            if (numberGrade >= bounds[0] && numberGrade <= bounds[1]) {
                letterGrade = letter;
                break;
            }
        }

        // update final grade for student
        GradeDAO.getInstance().updateFinalGrade(buid, courseId, absolute, percentage, deduction, letterGrade);
    }

    // get mean, median, standard deviation for the selected grade
    public String[] calculateStats(String courseId, String ruleId, String studentType) {
        calculateScores(courseId);
        double[] results;

        if (ruleId.equalsIgnoreCase(Config.FINALGRADESTATS)) {
            results = calculateFinalGradeStats(courseId, studentType);
        }
        else {
            results = calculateRuleStats(courseId, ruleId, studentType);
        }

        String[] stats = new String[3];
        // DecimalFormat num = new DecimalFormat("##.##");
        DecimalFormat percentage = new DecimalFormat("##.##%");
        stats[0] = percentage.format(results[0]);
        stats[1] = percentage.format(results[1]);
        stats[2] = percentage.format(results[2]);

        return stats;
    }

    // get mean, median, standard deviation for a grade except for the final grade
    private double[] calculateRuleStats(String courseId, String ruleId, String studentType) {
        // get students of selected type
        List<String> students = getStudentsOfType(courseId, studentType);

        double[] results = new double[3];
        int count = students.size();
        if (count == 0) {
            return new double[]{0, 0, 0};
        }

        // calculate stats
        double totalPercentage = 0;
        List<Double> scores = new ArrayList<>();
        for (String buid : students) {
            double percentageScore = GradeDAO.getInstance().getGrade(buid, ruleId).getPercentage();
            totalPercentage += percentageScore;
            scores.add(percentageScore);
        }
        results[0] = totalPercentage / count;
        results[1] = calcMedian(scores);
        results[2] = calcSD(scores, results[0]);

        return results;
    }

    // get mean, median, standard deviation for final grade
    private double[] calculateFinalGradeStats(String courseId, String studentType) {
        // get students of selected type
        List<String> students = getStudentsOfType(courseId, studentType);

        double[] results = new double[3];
        int count = students.size();
        if (count == 0) {
            return new double[]{0, 0, 0};
        }

        // calculate stats
        double totalPercentage = 0;
        List<Double> scores = new ArrayList<>();
        for (String buid : students) {
            double percentageScore = StudentService.getInstance().getStudent(buid, courseId).getFinalGrade().getPercentage();
            System.out.println("This score is: " + percentageScore);
            totalPercentage += percentageScore;
            scores.add(percentageScore);
        }
        System.out.println(totalPercentage);
        results[0] = totalPercentage / count;
        results[1] = calcMedian(scores);
        results[2] = calcSD(scores, results[0]);

        return results;
    }

    // get selected type of students; return their buid
    private List<String> getStudentsOfType(String courseId, String studentType) {
        Course course = CourseService.getInstance().getCourse(courseId);

        List<String> students = new ArrayList<>();
        if (studentType.equalsIgnoreCase("undergraduate")) {
            for (String buid : course.getStudents().keySet()) {
                Student student = StudentService.getInstance().getStudent(buid, courseId);
                if (student instanceof UndergraduateStudent) {
                    students.add(buid);
                }
            }
        }
        else if (studentType.equalsIgnoreCase("graduate")) {
            for (String buid : course.getStudents().keySet()) {
                Student student = StudentService.getInstance().getStudent(buid, courseId);
                if (student instanceof GraduateStudent) {
                    students.add(buid);
                }
            }
        }
        else {
            students.addAll(course.getStudents().keySet());
        }

        return students;
    }


    private double calcMedian(List<Double> scores) {
        Collections.sort(scores);
        return (scores.get((scores.size()-1)/2) + scores.get(scores.size()/2)) / 2.0;
    }

    private double calcSD(List<Double> scores, double mean) {
        double deviation = 0;
        for (int i = 0; i < scores.size(); i++) {
            deviation += Math.pow(scores.get(i) - mean, 2);
        }
        return Math.sqrt(deviation / scores.size());
    }


}
