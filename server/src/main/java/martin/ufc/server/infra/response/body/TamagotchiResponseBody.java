package martin.ufc.server.infra.response.body;

import martin.ufc.model.history.HistoryAction;
import martin.ufc.model.tamagotchi.Tamagotchi;

public class TamagotchiResponseBody implements ResponseBody {
    private Tamagotchi tamagotchi;
    private HistoryAction history;

    public TamagotchiResponseBody(Tamagotchi tamagotchi, HistoryAction history) {
        this.tamagotchi = tamagotchi;
        this.history = history;
    }

    @Override
    public String toJSON() {
        return "{ \"tamagotchi\": " + tamagotchi.toJSON() + "," +
                "\"historyAction\": " + history.toJSON() + "}";
    }
}
