package db;

import utils.ErrCode;

import java.sql.*;

public class Operations {
    public static final String url = "jdbc:sqlite:src/db/GradingSystem.db";

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
            c = DriverManager.getConnection(url);
        } catch (Exception e) {
            System.err.println(e);
        }
        return c;
    }

    public static void main(String[] args) {
        if(Operations.testDBConnection()) {
            System.out.println("Success");
        }
    }
}
