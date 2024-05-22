package martin.ufc.persistence.database;

import martin.ufc.exception.SQLiteException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteConnection {
    private Connection connection;
    private static SQLiteConnection INSTANCE;
    private static final String URL = "jdbc:sqlite:tamagotchi.db";

    private SQLiteConnection() throws SQLiteException {
        try {
            connection = DriverManager.getConnection(URL);
        } catch (SQLException exception) {
            throw new SQLiteException("Fail while connecting to SQLite database");
        }
    }

    public static SQLiteConnection getInstance() throws SQLiteException {
        if (INSTANCE == null) {
            INSTANCE = new SQLiteConnection();
        }
        return INSTANCE;
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

    public Statement createStatement() throws SQLiteException {
        try {
            return connection.createStatement();
        } catch (SQLException e) {
            throw new SQLiteException("Fail while creating statement with SQLite database");
        }
    }
}