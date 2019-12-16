package controller;

import model.*;
import service.GradingRuleService;
import service.ScoreService;
import service.StudentService;
import utils.Config;

import java.util.Map;

public class ShowEditStudentController {
    public static String getYearOfStudent(Student student){
        String year = "";
        if(student instanceof UndergraduateStudent){
            year = Config.UNDERGRADUATE;
        }else if(student instanceof GraduateStudent){
            year = Config.GRADUATE;
        }
        return year;
    }

    public static GradingRule getGradingRuleByID(String courseID, String GradingRuleID){
        return GradingRuleService.getInstance().getGradingRuleByID(courseID,GradingRuleID);
    }

    public static int updateStudent(String firstname, String midname, String lastname, String buid, String comment, String courseId){
        return StudentService.getInstance().updateStudent(firstname, midname, lastname, buid, comment, courseId);
    }

    public static int updateGradeComment(String courseId, String buid, String ruleId, String comment){
        return ScoreService.getInstance().updateGradeComment(courseId,buid,ruleId,comment);
    }

    public static int addStudent(String firstname, String midname, String lastname, String buid, String year, String comment, String courseId){
        return StudentService.getInstance().addStudent(firstname, midname, lastname, buid, year, comment, courseId);
    }

    public static String getCommentForGrade(String ruleID, Student student){
        Map<String,Grade> gs = student.getGrades();
        return gs.get(ruleID).getComment();
    }
}
