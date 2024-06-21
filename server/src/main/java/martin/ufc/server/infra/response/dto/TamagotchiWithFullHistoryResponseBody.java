package martin.ufc.server.infra.response.dto;

import martin.ufc.model.history.History;
import martin.ufc.model.tamagotchi.Tamagotchi;

public class TamagotchiWithFullHistoryResponseBody implements ResponseBody {
    private final Tamagotchi tamagotchi;
    private final History history;

    public TamagotchiWithFullHistoryResponseBody(Tamagotchi tamagotchi, History history) {
        this.tamagotchi = tamagotchi;
        this.history = history;
    }

    @Override
    public String toJSON() {
        return "{ \"tamagotchi\": " + tamagotchi.toJSON() + "," +
                history.toJSON() + "}";
    }
}
