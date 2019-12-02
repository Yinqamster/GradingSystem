package db;

import model.*;

import java.sql.PreparedStatement;
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
        PreparedStatement preparedStatement = DBUtil.getConnection().prepareStatement(selectSql);
        return null;
    }

    public int updateStudent(Student student, String courseID) throws SQLException{
        return 0;
    }
}
