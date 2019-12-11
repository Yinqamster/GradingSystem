package service;

import model.*;
import utils.Config;
import utils.ErrCode;
import db.GradeDAO;

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
        Student student = course.getStudents().get(buid);
        if (student == null) {
            return ErrCode.STUDENTNOTEXIST.getCode();
        }

        // update score
        for (String ruleId : scores.keySet()) {
            GradingRule rule = breakdown.getGradingRules().get(ruleId);
            // only update scores that is not composite
            if (rule.getChildren() == null || rule.getChildren().size() == 0) {
                Grade grade = student.getGrades().get(ruleId);
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
        Grade grade = student.getGrades().get(ruleId);

        grade.setComment(comment);
        return GradeDAO.getInstance().updateGrade(ruleId, buid, comment);
    }

    // calculate and update any composite score, i.e. grade that is made up of sub-grades
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
        Map<String, Student> students = course.getStudents();

        // calculate score for each student
        for (String buid : students.keySet()) {
            Student student = students.get(buid);
            double totalPercentage = 0;
            double totalAbsolute = 0;
            double totalDeduction = 0;

            Map<String, Grade> grades = student.getGrades();
            for (String ruleId : grades.keySet()) {
                Grade grade = grades.get(ruleId);
                double[] result = calculateScore(breakdown, ruleId, grades);
                // double fullScore = breakdown.getGradingRules().get(ruleId).getFullScore();
                // double absolute = calculateScore(breakdown, ruleId, grades);
                grade.setAbsolute(result[0]);
                grade.setPercentage(result[1]);
                grade.setDeduction(result[2]);

                // update this grade in DB
                GradeDAO.getInstance().upgradeGrade(ruleId, buid, grade);

                // add to final score if this is a category
                GradingRule rule = breakdown.getGradingRules().get(ruleId);
                if (rule.getParentID() == "") {
                    totalPercentage += result[0];
                    totalAbsolute += result[1];
                    totalDeduction += result[2];
                }
            }
            // update final grade for this student
            calculateFinalGrade(buid, courseId, breakdown, student, totalAbsolute, totalPercentage, totalDeduction);
        }

        return ErrCode.OK.getCode();
    }

    // calculate score for a grading rule
    // return the absolute score, percentage score, deduction score for this rule
    private double[] calculateScore(Breakdown breakdown, String ruleId, Map<String, Grade> grades) {
        double[] results = new double[3];
        GradingRule rule = breakdown.getGradingRules().get(ruleId);
        // not a composite rule
        if (rule.getChildren() == null || rule.getChildren().size() == 0) {
            results[0] = grades.get(ruleId).getAbsolute();
            results[1] = grades.get(ruleId).getPercentage();
            results[3] = grades.get(ruleId).getDeduction();
        }
        // a composite rule with sub-grades
        List<GradingRule> subRules = rule.getChildren();
        for (GradingRule subRule : subRules) {
            double proportion = breakdown.getGradingRules().get(subRule.getId()).getProportion();
            double[] subResults = calculateScore(breakdown, subRule.getId(), grades);
            results[0] += (subResults[0] * proportion);
            results[1] += (subResults[1] * proportion);
            results[2] += (subResults[2] * proportion);
        }
        return results;
    }


    // calculate and update the final grade for a student
    private void calculateFinalGrade(String buid, String courseId, Breakdown breakdown, Student student, double absolute, double percentage, double deduction) {
        // calculate letter grade
        Map<String, double[]> letterRule = breakdown.getLetterRule();
        String letterGrade = "";
        double toAbsolute = percentage * 100;
        for (String letter : letterRule.keySet()) {
            if (toAbsolute <= letterRule.get(letter)[0] && toAbsolute >= letterRule.get(letter)[1]) {
                letterGrade = letter;
                break;
            }
        }

        // update final grade for student
        FinalGrade finalGrade = student.getFinalGrade();
        finalGrade.setAbsolute(absolute);
        finalGrade.setPercentage(percentage);
        finalGrade.setDeduction(deduction);
        finalGrade.setLetterGrade(letterGrade);
        // update final grade in DB
        GradeDAO.getInstance().updateFinalGrade(buid, courseId, absolute, percentage, deduction, letterGrade);
    }

    public String[] calculateStats(String courseId, String ruleId, String studentType) {
        calculateScores(courseId);
        double[] results;

        if (studentType.equalsIgnoreCase("undergraduate")) {
            results = calcUnderStats(courseId, ruleId);
        }
        else if (studentType.equalsIgnoreCase("graduate")) {
            results = calcGradStats(courseId, ruleId);
        }
        else {
            results = calcAllStats(courseId, ruleId);
        }

        String[] stats = new String[3];
        DecimalFormat num = new DecimalFormat("##.##");
        DecimalFormat percentage = new DecimalFormat("##.##%");
//        stats[0] = num.format(results[0]) + " " + percentage.format(results[0] / results[3]);
//        stats[1] = num.format(results[1]) + " " + percentage.format(results[1] / results[3]);
//        stats[2] = num.format(results[2]) + " " + percentage.format(results[2] / results[3]);

        stats[0] = percentage.format(results[0]);
        stats[1] = percentage.format(results[1]);
        stats[2] = percentage.format(results[2]);

        return stats;
    }

    private double[] calcAllStats(String courseId, String ruleId) {
        Course course = CourseService.getInstance().getCourse(courseId);
        Map<String, Student> students = course.getStudents();
        int count = students.size();
//        Map<String, GradingRule> rules = course.getBreakdown().getGradingRules();
//        GradingRule rule = rules.get(ruleId);
//        double fullScore = 1.0;
//        System.out.println(ruleId);
//        System.out.println(rule.getName());
//        if(!rule.getName().equals(Config.FINALRULENAME)){
//            fullScore = rule.getFullScore();
//        }
        double total = 0;
        List<Double> scores = new ArrayList<>();

        for (String buid : students.keySet()) {
            Student student = students.get(buid);
            if(ruleId.equals(Config.FINALRULENAME)) {
                total += student.getFinalGrade().getPercentage();
                scores.add(student.getFinalGrade().getPercentage());
            }
            else {
                Grade grade = student.getGrades().get(ruleId);
//            total += grade.getAbsolute();
                total += grade.getPercentage();
                scores.add(grade.getPercentage());
            }

        }

//        double mean = total / (fullScore * count);
        double mean = total / count;
        double median = calcMedian(scores);
        double sd = calcSD(scores, mean);
        double[] results = {mean, median, sd};

        return results;
    }

    private double[] calcUnderStats(String courseId, String ruleId) {
        Course course = CourseService.getInstance().getCourse(courseId);
        Map<String, Student> students = course.getStudents();
        int count = 0;
        Map<String, GradingRule> rules = course.getBreakdown().getGradingRules();
        double fullScore = rules.get(ruleId).getFullScore();
        double totalScore = 0;
        List<Double> scores = new ArrayList<>();

        for (String buid : students.keySet()) {
            Student student = students.get(buid);
            if (student instanceof UndergraduateStudent) {
                double score = student.getGrades().get(ruleId).getAbsolute();
                totalScore += score;
                count += 1;
                scores.add(score);
            }
        }

        double mean = totalScore / (fullScore * count);
        double median = calcMedian(scores);
        double sd = calcSD(scores, mean);
        double[] results = {mean, median, sd, fullScore};

        return results;
    }

    private double[] calcGradStats(String courseId, String ruleId) {
        Course course = CourseService.getInstance().getCourse(courseId);
        Map<String, Student> students = course.getStudents();
        int count = 0;
        Map<String, GradingRule> rules = course.getBreakdown().getGradingRules();
        double fullScore = rules.get(ruleId).getFullScore();
        double totalScore = 0;
        List<Double> scores = new ArrayList<>();

        for (String buid : students.keySet()) {
            Student student = students.get(buid);
            if (student instanceof GraduateStudent) {
                double score = student.getGrades().get(ruleId).getAbsolute();
                totalScore += score;
                count += 1;
                scores.add(score);
            }
        }

        double mean = totalScore / (fullScore * count);
        double median = calcMedian(scores);
        double sd = calcSD(scores, mean);
        double[] results = {mean, median, sd, fullScore};

        return results;
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
