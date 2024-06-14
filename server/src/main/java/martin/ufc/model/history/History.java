package martin.ufc.model.history;

import martin.ufc.model.JSONfier;

import java.util.ArrayList;
import java.util.List;

public class History implements JSONfier {
    private final List<HistoryAction> historyActions;

    public History() {
        historyActions = new ArrayList<>();
    }

    @Override
    public String toJSON() {
        StringBuilder json = new StringBuilder("\"history\": [");
        for (int cont = 0; cont < historyActions.size(); cont++) {
            json.append(historyActions.get(cont).toJSON());
            if (cont < historyActions.size()-1) {
                json.append(",");
            }
        }
        json.append("]");
        return json.toString();
    }

    public void add(HistoryAction historyAction) {
        historyActions.add(historyAction);
    }
}
