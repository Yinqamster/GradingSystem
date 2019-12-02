package db;

import model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseDAO extends DAOImpl{

    public static CourseDAO courseDAO = new CourseDAO();

    private CourseDAO() {

    }

    public static CourseDAO getInstance() {
        return courseDAO;
    }

    public Course getCourse(String courseID) throws SQLException {
        String selectSql = "SELECT * FROM course WHERE course_id = ?";
        ResultSet resultSet = super.getValue(selectSql, courseID);
        String name = resultSet.getString("name");
        String section = resultSet.getString("section");
        String semester = resultSet.getString("section");
        String description = resultSet.getString("description");

        String selectStudentSql = "SELECT * FROM student gs INNER JOIN " +
                "course_student_relationship csr ON csr.student_id = gs.buid AND csr.course_id = ?";
        ResultSet studentMapResultSet = super.getValue(selectStudentSql, courseID, courseID);
        Map<String, Student> map = new HashMap<>();
        while(studentMapResultSet.next()) {
            String buid = studentMapResultSet.getString("buid");
            Name studentName = NameDAO.getInstance().getName(buid);
            int status = studentMapResultSet.getInt("status");
            String comment = studentMapResultSet.getString("comment");
            double bonus = studentMapResultSet.getDouble("bonus");
            int category = studentMapResultSet.getInt("category");
            List<Grade> gradeList = GradeDAO.getInstance().getGradeList(buid);
            Student student = null;
            if(category == 0) {
                student = new UndergraduateStudent(studentName, buid, status, bonus, comment, gradeList);
            }
            else if(category == 1){
                student = new GraduateStudent(studentName, buid, status, bonus, comment, gradeList);
            }
            else {
                System.err.println("The student has no category");
            }
            map.put(buid, student);
        }
        return new Course(name, section, semester, description, map);
    }

    public int updateStudent(Student student, String courseID) throws SQLException{
        return 0;
    }
}
