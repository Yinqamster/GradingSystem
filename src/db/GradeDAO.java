package db;

import model.FinalGrade;
import model.Grade;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GradeDAO extends DAOImpl {

    public static GradeDAO gradeDAO = new GradeDAO();

    private GradeDAO() {
    }

    public static GradeDAO getInstance() {
        return gradeDAO;
    }

    public List<Grade> getGradeList(String BUID) throws SQLException {
        List<Grade> result = new ArrayList<>();
        String selectSql = "SELECT * FROM grade WHERE fk_student = ?";
        ResultSet resultSet = super.getValue(selectSql, BUID);
        double absolute_score = resultSet.getDouble("absolute_score");
        double percentage_score = resultSet.getDouble("percentage_score");
        double deduction_score = resultSet.getDouble("deduction_score");
        String comment = resultSet.getString("comment");
        String gradeName = resultSet.getString("fk_grading_rule");
        while(resultSet.next()) {
            if(gradeName.equalsIgnoreCase("Final")) {
                String letter = resultSet.getString("letter_grade");
                Grade finalGrade = new FinalGrade(gradeName, absolute_score, percentage_score,
                        deduction_score, comment, letter);
                result.add(finalGrade);
            }
            else {
                Grade grade = new Grade(gradeName, absolute_score, percentage_score, deduction_score, comment);
                result.add(grade);
            }
        }
        return result;
    }
}
