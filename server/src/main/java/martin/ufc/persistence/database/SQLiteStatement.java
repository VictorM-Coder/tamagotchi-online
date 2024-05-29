package martin.ufc.persistence.database;

import martin.ufc.exception.SQLiteException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLiteStatement {
    private PreparedStatement preparedStatement;
    private int indexValue = 1;

    public SQLiteStatement(String sql) throws SQLiteException {
        SQLiteConnection connection = SQLiteConnection.getInstance();
        preparedStatement = connection.preparedStatement(sql);
    }

    public SQLiteStatement setString(String value) throws SQLiteException {
        try {
            preparedStatement.setString(indexValue, value);
            indexValue++;
        } catch (SQLException e) {
            throw new SQLiteException("Fail while setting string value for a statement");
        }

        return this;
    }

    public SQLiteStatement setInt(int value) throws SQLiteException {
        try {
            preparedStatement.setInt(indexValue, value);
            indexValue++;
        } catch (SQLException e) {
            throw new SQLiteException("Fail while setting int value for a statement");
        }

        return this;
    }

    public int executeInsert() throws SQLiteException {
        try {
            preparedStatement.execute();
            return preparedStatement.getGeneratedKeys().getInt(1);
        } catch (SQLException e) {
            throw new SQLiteException("Fail while executing insert in a statement");
        }
    }

    public void executeUpdate() throws SQLiteException {
        try {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLiteException("Fail while executing update in a statement");
        }
    }

    public ResultSet executeQuery() throws SQLiteException {
        try {
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new SQLiteException("Fail while finding a value from database");
        }
    }

    public void close() throws SQLiteException {
        try {
            preparedStatement.close();
        } catch (SQLException e) {
            throw new SQLiteException("Fail while closing statement");
        }
    }
}
