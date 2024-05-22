package martin.ufc.persistence.repository;

import martin.ufc.exception.SQLiteException;
import martin.ufc.model.tamagotchi.Attribute;
import martin.ufc.model.tamagotchi.Tamagotchi;
import martin.ufc.persistence.database.SQLiteStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

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

    public static Tamagotchi findById(int id) throws SQLiteException {
        String sql = "SELECT * FROM tamagotchi_tb WHERE id = ?";
        SQLiteStatement statement = new SQLiteStatement(sql);

        try (ResultSet resultSet = statement.setInt(id).executeQuery();) {
            String name = resultSet.getString("name");
            boolean isSleeping = resultSet.getInt("isSleeping") == 1;
            Attribute food = new Attribute(resultSet.getInt("food"));
            Attribute happy = new Attribute(resultSet.getInt("happy"));
            Attribute energy = new Attribute(resultSet.getInt("energy"));
            LocalDate birthday = LocalDate.parse(resultSet.getString("birthday"));

            Tamagotchi tamagotchi = new Tamagotchi(id, name, birthday, isSleeping, food, happy, energy);
            statement.close();
            return tamagotchi;
        } catch (SQLException e) {
            throw new SQLiteException(e.getMessage());
        }
    }

    public static void update(int id, Tamagotchi tamagotchi) throws SQLiteException {
        String sql = "UPDATE tamagotchi_tb " +
                "SET isSleeping = ?, " +
                "food = ?, " +
                "happy = ?, " +
                "energy = ? " +
                "WHERE id = ?";

        SQLiteStatement statement = new SQLiteStatement(sql);
        statement.setInt(tamagotchi.isSleeping()? 1: 0)
                .setInt(tamagotchi.getFood())
                .setInt(tamagotchi.getHappy())
                .setInt(tamagotchi.getEnergy())
                .setInt(id)
                .executeUpdate();

        statement.close();
    }
}
