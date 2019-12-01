package db;

import java.sql.*;

public class DBUtil {
    public static final String url = "jdbc:sqlite:src/db/GradingSystem.db";
    public static final String JDBCName = "org.sqlite.JDBC";
    // call this method firstly
    public static boolean testDBConnection() {
        if (getConnection() != null) {
            System.out.println("Connect to DB successfully");
            return true;
        } else  {
            return false;
        }
    }

    public static Connection getConnection() {
        Connection c = null;
        try {
            Class.forName(JDBCName);
            c = DriverManager.getConnection(url);
        } catch (Exception e) {
            System.err.println(e);
        }
        return c;
    }

    public static void closeConnection(Connection connection) throws SQLException {
        if (null != connection){
            connection.close();
        }
    }
}
