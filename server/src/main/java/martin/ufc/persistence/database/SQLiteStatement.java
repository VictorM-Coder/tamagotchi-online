package martin.ufc.persistence.database;

import martin.ufc.exception.SQLiteException;

import java.sql.PreparedStatement;
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
            throw new SQLiteException("Fail while setting value for a statement");
        }

        return this;
    }

    public SQLiteStatement setInt(int value) throws SQLiteException {
        try {
            preparedStatement.setInt(indexValue, value);
            indexValue++;
        } catch (SQLException e) {
            throw new SQLiteException("Fail while setting value for a statement");
        }

        return this;
    }

    public void execute() throws SQLiteException {
        try {
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new SQLiteException("Fail while setting value for a statement");
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
