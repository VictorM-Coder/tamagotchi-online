package martin.ufc.server.infra.handlers;

import martin.ufc.exception.InternalException;
import martin.ufc.exception.TamagotchiNotFoundException;
import martin.ufc.model.history.HistoryAction;
import martin.ufc.model.tamagotchi.Tamagotchi;
import martin.ufc.server.infra.request.action.ActionRequest;
import martin.ufc.server.infra.request.no_connected.ConnectionRequest;
import martin.ufc.server.infra.request.no_connected.CreationRequest;
import martin.ufc.server.infra.response.dto.TamagotchiResponseBody;
import martin.ufc.server.infra.response.dto.TamagotchiWithFullHistoryResponseBody;
import martin.ufc.server.infra.services.HistoryActionService;
import martin.ufc.server.infra.services.TamagotchiService;
import martin.ufc.util.LoggerUtil;
//TODO remover logs e refatorar código
public class ActionsHandler {
    private final ConnectionRequest connectionRequest;

    public ActionsHandler(ConnectionRequest connectionRequest) {
        this.connectionRequest = connectionRequest;
    }

    public TamagotchiResponseBody handleEatAction(ActionRequest actionRequest) throws TamagotchiNotFoundException, InternalException {
        int id = getIdFromMessage();

        Tamagotchi tamagotchi = TamagotchiService.feedTamagotchi(id);
        HistoryAction historyAction = addHistoryAction(actionRequest.getMessageType().toString());
        LoggerUtil.logTrace("tamagotchi eating");

        return new TamagotchiResponseBody(tamagotchi, historyAction);
    }

    public TamagotchiResponseBody handleSleepAction(ActionRequest actionRequest) throws TamagotchiNotFoundException, InternalException {
        int id = getIdFromMessage();

        Tamagotchi tamagotchi = TamagotchiService.putTamagotchiToSleep(id);
        HistoryAction historyAction = addHistoryAction(actionRequest.getMessageType().toString());
        LoggerUtil.logTrace("tamagotchi sleeping");

        return new TamagotchiResponseBody(tamagotchi, historyAction);
    }


    public TamagotchiResponseBody handleAwakeAction(ActionRequest actionRequest) throws TamagotchiNotFoundException, InternalException {
        int id = getIdFromMessage();

        Tamagotchi tamagotchi = TamagotchiService.awakeTamagotchi(id);
        HistoryAction historyAction = addHistoryAction(actionRequest.getMessageType().toString());
        LoggerUtil.logTrace("tamagotchi wake up");

        return new TamagotchiResponseBody(tamagotchi, historyAction);
    }

    public TamagotchiResponseBody handlePlayAction(ActionRequest actionRequest) throws TamagotchiNotFoundException, InternalException {
        int id = getIdFromMessage();

        Tamagotchi tamagotchi = TamagotchiService.playWithTamagotchi(id);
        HistoryAction historyAction = addHistoryAction(actionRequest.getMessageType().toString());
        LoggerUtil.logTrace("tamagotchi playing");

        return new TamagotchiResponseBody(tamagotchi, historyAction);
    }

    public TamagotchiWithFullHistoryResponseBody handleGetAction(ActionRequest actionRequest) throws InternalException, TamagotchiNotFoundException {
        int id = getIdFromMessage();

        Tamagotchi tamagotchi = TamagotchiService.findTamagotchiById(id);
        addHistoryAction(actionRequest.getMessageType().toString());
        LoggerUtil.logTrace("get tamagotchi: " + tamagotchi.toJSON());

        return new TamagotchiWithFullHistoryResponseBody(tamagotchi, HistoryActionService.getHistoryActionsForATamagotchi(id));
    }

    private int getIdFromMessage() {
        return connectionRequest.getId();
    }

    private HistoryAction addHistoryAction(String actionType) throws InternalException {
        return HistoryActionService.createHistoryAction(connectionRequest.getOwner(), actionType, getIdFromMessage());
    }
}
