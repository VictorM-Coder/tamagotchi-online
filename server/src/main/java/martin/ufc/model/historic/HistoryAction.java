package martin.ufc.model.historic;

import martin.ufc.model.JSONfier;
import martin.ufc.server.infra.request.message.ActionType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HistoryAction implements JSONfier {
    private int id;
    private String username;
    private int tamagotchiId;
    private ActionType action;
    private LocalDateTime dateTime;

    @Override
    public String toJSON() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String dateTimeString = dateTime.format(formatter);
        return "{" +
                "\"id\":" + id + "," +
                "\"username\":\"" + username + "\"," +
                "\"tamagotchiId\":" + tamagotchiId + "," +
                "\"action\":\"" + action + "\"," +
                "\"dateTime\":\"" + dateTimeString + "\"" +
                "}";
    }

    public void setId(int id) {
        this.id = id;
    }

    public HistoryAction(String username, int tamagotchiId, ActionType action) {
        this.username = username;
        this.tamagotchiId = tamagotchiId;
        this.action = action;
        this.dateTime = LocalDateTime.now();
    }

    public HistoryAction(String username, int tamagotchiId, ActionType action, LocalDateTime dateTime) {
        this.username = username;
        this.tamagotchiId = tamagotchiId;
        this.action = action;
        this.dateTime = dateTime;
    }

    public String getUsername() {
        return username;
    }

    public int getTamagotchiId() {
        return tamagotchiId;
    }

    public ActionType getAction() {
        return action;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
