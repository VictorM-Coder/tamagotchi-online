package martin.ufc.server.infra.services;

import martin.ufc.exception.InternalException;
import martin.ufc.exception.SQLiteException;
import martin.ufc.model.history.History;
import martin.ufc.model.history.HistoryAction;
import martin.ufc.persistence.repository.HistoryActionsRepository;
import martin.ufc.server.infra.request.message.ActionType;

public class HistoryActionService {
    public HistoryAction createHistoryAction(String username, ActionType actionType, int tamagotchiId) throws InternalException {
        try {
            HistoryAction historyAction = new HistoryAction(username, tamagotchiId, actionType);
            int id = HistoryActionsRepository.add(historyAction);

            historyAction.setId(id);
            return historyAction;
        } catch (SQLiteException e) {
            throw new InternalException("Fail while saving actions history");
        }
    }

    public History getHistoryActionsForATamagotchi(int tamagotchiId) throws InternalException {
        try {
            return HistoryActionsRepository.getHistoryActionsForATamagotchi(tamagotchiId);
        } catch (SQLiteException e) {
            throw  new InternalException("Fail while getting the actions history");
        }
    }
}
