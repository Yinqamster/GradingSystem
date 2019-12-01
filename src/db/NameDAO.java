package db;

import model.Name;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NameDAO extends DAOImpl{

    private static NameDAO nameDAO = new NameDAO();

    public static NameDAO getInstance() {
        return nameDAO;
    }

    private NameDAO() {

    }

    public Name getName(String BUID) throws SQLException {
        String selectSql = "SELECT first_name, middle_name, last_name FROM student WHERE buid = ?";
        ResultSet resultSet = super.getValue(selectSql, BUID);
        String firstName = resultSet.getString("first_name");
        String middleName = resultSet.getString("middle_name");
        String lastName = resultSet.getString("last_name");
        return new Name(firstName, middleName, lastName);
    }

    public int updateName(String BUID, Name name) throws SQLException {
        String firstName = name.getFirstName();
        String middleName = name.getMiddleName();
        String lastName = name.getLastName();
        String updateSql = "UPDATE student SET first_name = ?, middle_name = ?, last_name = ? WHERE buid = ?";
        return super.update(updateSql, firstName, middleName, lastName, BUID);
    }
}
