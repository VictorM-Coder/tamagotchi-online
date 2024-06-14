package martin.ufc.persistence.database;

import martin.ufc.exception.SQLiteException;

import java.sql.*;

public class SQLiteConnection {
    private final Connection connection;
    private static SQLiteConnection instance;
    private static final String URL = "jdbc:sqlite:tamagotchi.db";

    private SQLiteConnection() throws SQLiteException {
        try {
            connection = DriverManager.getConnection(URL);
        } catch (SQLException exception) {
            throw new SQLiteException("Fail while connecting to SQLite database");
        }
    }

    public static SQLiteConnection getInstance() throws SQLiteException {
        if (instance == null) {
            instance = new SQLiteConnection();
        }
        return instance;
    }

    public void closeConnection() throws SQLiteException {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException exception) {
            throw new SQLiteException("Fail while disconnecting to SQLite database");
        }
    }

    public PreparedStatement preparedStatement(String sql) throws SQLiteException {
        try {
            return connection.prepareStatement(sql);
        } catch (SQLException e) {
            throw new SQLiteException("Fail while creating prepared statement with SQLite database");
        }
    }
}
