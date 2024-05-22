package martin.ufc.persistence.repository;

import martin.ufc.exception.SQLiteException;
import martin.ufc.persistence.database.SQLiteStatement;

public class TamagotchiRepository {
    public static void createTable() throws SQLiteException {
        final String sql;
        sql = "CREATE TABLE IF NOT EXISTS tamagotchi_db (\n" +
                "  id INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                "  name TEXT NOT NULL,\n" +
                "  birthday TEXT,\n" +
                "  isleeping INTEGER,\n" +
                "  food INTEGER,\n" +
                "  happy INTEGER,\n" +
                "  energy INTEGER\n" +
                ");\n";

        SQLiteStatement.executeSQL(sql);
    }
}
