package martin.ufc.model.history;

import martin.ufc.model.JSONfier;
import martin.ufc.server.infra.request.types.RequestType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HistoryAction implements JSONfier {
    private int id;
    private String username;
    private int tamagotchiId;
    private RequestType action;
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

    public HistoryAction(String username, int tamagotchiId, RequestType action) {
        this.username = username;
        this.tamagotchiId = tamagotchiId;
        this.action = action;
        this.dateTime = LocalDateTime.now();
    }

    public HistoryAction(String username, int tamagotchiId, RequestType action, LocalDateTime dateTime) {
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

    public RequestType getAction() {
        return action;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
