package db;

import model.*;
import utils.ErrCode;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO extends DAOImpl{

    // Student has a Category in database, 0 for undergraduate, 1 for graduate

    public static StudentDAO studentDAO = new StudentDAO();

    private StudentDAO() {
    }

    public static StudentDAO getInstance() {
        return studentDAO;
    }

    public Student getStudent(String BUID) throws SQLException {
        String selectSql = "SELECT * FROM student WHERE buid = ?";
        ResultSet resultSet = super.getValue(selectSql, BUID);
        Name name = NameDAO.getInstance().getName(BUID);
        int status = Integer.parseInt(resultSet.getString("status"));
        double bonus = Double.parseDouble(resultSet.getString("bonus"));
        String comment = resultSet.getString("comment");
        List<Grade> gradeList = GradeDAO.getInstance().getGradeList(BUID);
        int category = resultSet.getInt("category");
        if(category == 1) {
            return new GraduateStudent(name, BUID, status, bonus, comment, gradeList);
        }
        else if(category == 0) {
            return new UndergraduateStudent(name, BUID, status, bonus, comment, gradeList);
        }
        else {
            System.err.println("The student does not have any category");
            return null;
        }
    }

    public int updateStudent(Student student) throws SQLException{
        String buid = student.getBuid();
        String firstName = student.getName().getFirstName();
        String middleName = student.getName().getMiddleName();
        String lastName = student.getName().getLastName();
        int status = student.getStatus();
        String comment = student.getComment();
        double bonus = student.getBonus();
        String updateSql = "REPLACE INTO student (buid, first_name, middle_name, " +
                    "last_name, status, comment, bonus, category) values (?, ?, ?, ?, ?, ?, ?, ?)";
        if(student instanceof GraduateStudent) {
            return super.update(updateSql, buid, firstName, middleName, lastName, status, comment, bonus, 1);
        }
        else if(student instanceof UndergraduateStudent) {
            return super.update(updateSql, buid, firstName, middleName, lastName, status, comment, bonus, 0);
        }
        else {
            return ErrCode.STUDENTTYPEERROR.getCode();
        }
    }

    public int deleteStudent(Student student) throws SQLException {
        String BUID = student.getBuid();
        String deleteSql = "DELETE FROM student WHERE buid = ?";
        return super.delete(deleteSql, BUID);
    }
}
