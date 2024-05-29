package martin.ufc.persistence.repository;

import martin.ufc.exception.SQLiteException;
import martin.ufc.model.historic.History;
import martin.ufc.model.historic.HistoryAction;
import martin.ufc.persistence.database.SQLiteStatement;
import martin.ufc.server.infra.request.message.ActionType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HistoryActionsRepository {
    private HistoryActionsRepository() {}

    public static int add(HistoryAction historyAction) throws SQLiteException {
        String sql = "INSERT INTO history_action_tb(username, tamagotchi_id, action, date_time)" +
                "values(?, ?, ?, ?)";

        SQLiteStatement statement = new SQLiteStatement(sql);
        int id = statement
                .setString(historyAction.getUsername())
                .setInt(historyAction.getTamagotchiId())
                .setString(historyAction.getAction().toString())
                .setString(historyAction.getDateTime().toString())
                .executeInsert();

        statement.close();
        return id;
    }

    public static History getHistoryActionsForATamagotchi(int tamagotchiId) throws SQLiteException {
        String sql = "SELECT * FROM history_action_tb WHERE tamagotchi_id = ?";
        SQLiteStatement statement = new SQLiteStatement(sql);
        History historyActions = new History();

        ResultSet resultSet = statement.setInt(tamagotchiId).executeQuery();

        try {
            while (resultSet.next()) {
                String username = resultSet.getString("username");
                ActionType action = ActionType.valueOf(resultSet.getString("action"));
                LocalDateTime dateTime = LocalDateTime.parse(resultSet.getString("date_time"));

                historyActions.add(new HistoryAction(username, tamagotchiId, action, dateTime));
            }
        } catch (SQLException e) {
            throw new SQLiteException(e.getMessage());
        }

        return historyActions;
    }
}
