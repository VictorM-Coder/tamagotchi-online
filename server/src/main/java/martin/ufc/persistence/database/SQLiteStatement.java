package martin.ufc.persistence.database;

import martin.ufc.exception.SQLiteException;

import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteStatement {
    public static void executeSQL(String sql) throws SQLiteException {
        SQLiteConnection connection = SQLiteConnection.getInstance();
        try (Statement statement = connection.createStatement()){
            statement.execute(sql);
        } catch (SQLException e) {
            throw new SQLiteException("Fail while executing sql: " + sql);
        }
    }
}
