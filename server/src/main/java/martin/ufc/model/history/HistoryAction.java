package martin.ufc.model.history;

import martin.ufc.model.JSONfier;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HistoryAction implements JSONfier {
    private int id;
    private final String username;
    private final int tamagotchiId;
    private final String action;
    private final LocalDateTime dateTime;

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

    public HistoryAction(String username, int tamagotchiId, String action) {
        this.username = username;
        this.tamagotchiId = tamagotchiId;
        this.action = action;
        this.dateTime = LocalDateTime.now();
    }

    public HistoryAction(String username, int tamagotchiId, String action, LocalDateTime dateTime) {
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

    public String getAction() {
        return action;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
