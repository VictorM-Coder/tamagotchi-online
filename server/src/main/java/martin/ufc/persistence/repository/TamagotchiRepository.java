package martin.ufc.persistence.repository;

import martin.ufc.exception.SQLiteException;
import martin.ufc.model.tamagotchi.Attribute;
import martin.ufc.model.tamagotchi.Tamagotchi;
import martin.ufc.persistence.database.SQLiteStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class TamagotchiRepository {
    public static int add(Tamagotchi tamagotchi) throws SQLiteException {
        String sql = "INSERT INTO tamagotchi_tb(name, birthday, isSleeping, food, happy, energy)" +
                "values(?, ?, ?, ?, ?, ?)";

        SQLiteStatement statement = new SQLiteStatement(sql);
        int id = statement
                .setString(tamagotchi.getName())
                .setString(tamagotchi.getBirthday().toString())
                .setInt(tamagotchi.isSleeping() ? 1 : 0)
                .setInt(tamagotchi.getFood())
                .setInt(tamagotchi.getHappy())
                .setInt(tamagotchi.getEnergy())
                .executeInsert();

        statement.close();
        return id;
    }

    public static Tamagotchi findById(int id) throws SQLiteException {
        String sql = "SELECT * FROM tamagotchi_tb WHERE id = ?";
        SQLiteStatement statement = new SQLiteStatement(sql);

        try (ResultSet resultSet = statement.setInt(id).executeQuery();) {
            Tamagotchi tamagotchi = convertResultSetToTamagotchi(resultSet);
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

    private static Tamagotchi convertResultSetToTamagotchi(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        boolean isSleeping = resultSet.getInt("isSleeping") == 1;
        Attribute food = new Attribute(resultSet.getInt("food"));
        Attribute happy = new Attribute(resultSet.getInt("happy"));
        Attribute energy = new Attribute(resultSet.getInt("energy"));
        LocalDate birthday = LocalDate.parse(resultSet.getString("birthday"));

        return new Tamagotchi(id, name, birthday, isSleeping, food, happy, energy);
    }
}
