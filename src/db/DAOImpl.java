package db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOImpl implements DAO {

    @Override
    public int update(Connection connection, String sql, Object... args) throws SQLException {
        if(connection == null) {
            throw new SQLException("Null connection");
        } else if (sql == null) {
            DBUtil.closeConnection(connection);
            throw new SQLException("Null SQL statement");
        }
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            preparedStatement.setObject(i + 1, args[i]);
        }
        return preparedStatement.executeUpdate();
    }

    @Override
    public int update(String sql, Object... args) throws SQLException {
        return this.update(DBUtil.getConnection(), sql, args);
    }

    @Override
    public int update(String sql) throws SQLException {
        return this.update(DBUtil.getConnection(), sql, (Object[])null);
    }

    @Override
    public int update(Connection connection, String sql) throws SQLException {
        return this.update(connection, sql, (Object[])null);
    }

    @Override
    public ResultSet getValue(Connection connection, String sql) throws SQLException {
        return this.getValue(connection, sql, (Object[])null);
    }

    @Override
    public ResultSet getValue(String sql) throws SQLException {
        return this.getValue(DBUtil.getConnection(), sql, (Object[])null);
    }

    @Override
    public ResultSet getValue(String sql, Object... args) throws SQLException {
        return this.getValue(DBUtil.getConnection(), sql, args);
    }

    @Override
    public ResultSet getValue(Connection connection, String sql, Object... args) throws SQLException {
        if(connection == null) {
            throw new SQLException("Null connection");
        } else if (sql == null) {
            DBUtil.closeConnection(connection);
            throw new SQLException("Null SQL statement");
        }
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for(int i = 0; i < args.length; i++) {
            preparedStatement.setObject(i + 1, args[i]);
        }
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Object> result = new ArrayList<>();
        int columnCount = resultSet.getMetaData().getColumnCount();
        for(int i = 1; i<= columnCount; i++) {
            result.add(resultSet.getObject(i));
            System.out.println(resultSet.getObject(i).toString());
        }
        return preparedStatement.executeQuery();
    }

    @Override
    public int delete(Connection connection, String sql, Object... args) throws SQLException {
        if(connection == null) {
            throw new SQLException("Null connection");
        } else if (sql == null) {
            DBUtil.closeConnection(connection);
            throw new SQLException("Null SQL statement");
        }
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for(int i = 0; i < args.length; i++) {
            preparedStatement.setObject(i + 1, args[i]);
        }
        return preparedStatement.executeUpdate();
    }

    @Override
    public int delete(Connection connection, String sql) throws SQLException {
        return this.delete(connection, sql, (Object[])null);
    }

    @Override
    public int delete(String sql, Object... args) throws SQLException {
        return this.delete(DBUtil.getConnection(), sql, args);
    }

    @Override
    public int delete(String sql) throws SQLException {
        return this.delete(DBUtil.getConnection(), sql, (Object[])null);
    }
}
