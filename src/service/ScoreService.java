package service;

import model.*;
import utils.ErrCode;

import java.util.*;
import java.text.DecimalFormat;

public class ScoreService {
    private static ScoreService instance = new ScoreService();
    public CourseService courseService = CourseService.getInstance();

    private ScoreService(){

    }
    public static ScoreService getInstance() {
//        if (instance == null) {
//            instance = new ScoreService();
//        }
        return instance;
    }

    public int updateRowScore(String courseId, String buid, Map<String, String> scores) {
        // TODO

        return ErrCode.OK.getCode();
    }

    public int calculateScores(String courseId) {
        // TODO

        return ErrCode.OK.getCode();
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
        stats[0] = num.format(results[0]) + " " + percentage.format(results[0] / results[3]);
        stats[1] = num.format(results[1]) + " " + percentage.format(results[1] / results[3]);
        stats[2] = num.format(results[2]) + " " + percentage.format(results[2] / results[3]);

        return stats;
    }

    private double[] calcAllStats(String courseId, String ruleId) {
        Course course = courseService.getCourse(courseId);
        Map<String, Student> students = course.getStudents();
        int count = students.size();
        Map<String, GradingRule> rules = course.getBreakdown().getGradingRules();
        double fullScore = rules.get(ruleId).getFullScore();
        double total = 0;
        List<Double> scores = new ArrayList<>();

        for (String buid : students.keySet()) {
            Student student = students.get(buid);
            Grade grade = student.getGrades().get(ruleId);
            total += grade.getAbsolute();
            scores.add(grade.getAbsolute());
        }

        double mean = total / (fullScore * count);
        double median = calcMedian(scores);
        double sd = calcSD(scores, mean);
        double[] results = {mean, median, sd, fullScore};

        return results;
    }

    private double[] calcUnderStats(String courseId, String ruleId) {
        Course course = courseService.getCourse(courseId);
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
        Course course = courseService.getCourse(courseId);
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
