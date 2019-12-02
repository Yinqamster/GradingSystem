package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface DAO {

    /**
     * INSERT,UPDATE,DELETE
     * @param sql：SQL statement
     * @param connection：Connection of database
     * @param args：Parameter of SQL
     */
    int update(Connection connection, String sql, Object... args) throws SQLException;

    /**
     * INSERT,UPDATE,DELETE
     * @param sql：SQL statement
     * @param args：Parameter of SQL
     */
    int update(String sql, Object... args) throws SQLException;

    /**
     * INSERT,UPDATE,DELETE
     * @param sql：SQL statement
     */
    int update(String sql) throws SQLException ;

    /**
     * INSERT,UPDATE,DELETE
     * @param sql：SQL statement
     * @param connection：Connection of database
     */
    int update(Connection connection, String sql) throws SQLException;

    /**
     * @param connection: Connection of database
     * @param sql: SQL statement
     * @return: Data value
     */
    List<Object> getValue(Connection connection, String sql) throws SQLException;

    /**
     * @param sql: SQL statement
     * @return: Data value
     */
    List<Object> getValue(String sql) throws SQLException;

    /**
     * @param sql: SQL statement
     * @param args: Parameter of SQL
     * @return: Data value
     */
    List<Object> getValue(String sql, Object... args) throws SQLException;

    /**
     * @param connection: Connection of database
     * @param sql: SQL statement
     * @param args: Parameter of SQL
     * @return: Data value
     */
    List<Object> getValue(Connection connection, String sql, Object... args) throws SQLException;

    /**
     * @param connection: Connection of database
     * @param sql: SQL statement
     * @param args: Parameter of SQL
     * @return: Data value
     */
    int delete(Connection connection, String sql, Object... args) throws SQLException;

    /**
     * @param connection: Connection of database
     * @param sql: SQL statement
     * @return: Data value
     */
    int delete(Connection connection, String sql) throws SQLException;

    /**
     * @param sql: SQL statement
     * @param args: Parameter of SQL
     * @return: Data value
     */
    int delete(String sql, Object... args) throws SQLException;

    /**
     * @param sql: SQL statement
     * @return: Data value
     */
    int delete(String sql) throws SQLException;
}
