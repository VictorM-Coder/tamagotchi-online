package martin.ufc.persistence.repository;

import martin.ufc.exception.SQLiteException;
import martin.ufc.model.tamagotchi.Tamagotchi;
import martin.ufc.persistence.database.SQLiteStatement;

public class TamagotchiRepository {
    public static void add(Tamagotchi tamagotchi) throws SQLiteException {
        String sql = "INSERT INTO tamagotchi_tb(name, birthday, isSleeping, food, happy, energy)" +
                "values(?, ?, ?, ?, ?, ?)";

        SQLiteStatement statement = new SQLiteStatement(sql);
        statement
                .setString(tamagotchi.getName())
                .setString(tamagotchi.getBirthday().toString())
                .setInt(tamagotchi.isSleeping()? 1: 0)
                .setInt(tamagotchi.getFood())
                .setInt(tamagotchi.getHappy())
                .setInt(tamagotchi.getEnergy())
                .execute();

        statement.close();
    }
}
