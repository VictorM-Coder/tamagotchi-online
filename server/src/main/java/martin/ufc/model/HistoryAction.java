package martin.ufc.model;

import martin.ufc.server.infra.request.message.ActionType;

import java.time.LocalDateTime;

public class HistoryAction {
    private int id;
    private String username;
    private int tamagotchiId;
    private ActionType action;
    private LocalDateTime dateTime;

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
