package martin.ufc.server.infra.handlers;

import martin.ufc.exception.InternalException;
import martin.ufc.exception.RequestException;
import martin.ufc.exception.TamagotchiNotFoundException;
import martin.ufc.model.history.HistoryAction;
import martin.ufc.model.tamagotchi.Tamagotchi;
import martin.ufc.server.infra.request.ActionRequest;
import martin.ufc.server.infra.request.ActionType;
import martin.ufc.server.infra.request.ConnectionRequest;
import martin.ufc.server.infra.response.body.TamagotchiWithFullHistoryResponseBody;
import martin.ufc.server.infra.response.body.TamagotchiResponseBody;
import martin.ufc.server.infra.services.HistoryActionService;
import martin.ufc.server.infra.services.TamagotchiService;
import martin.ufc.util.LoggerUtil;
//TODO remover logs e refatorar código
public class ActionsHandler {
    private final TamagotchiService tamagotchiService;
    private final HistoryActionService historyActionService;
    private final ConnectionRequest connectionRequest;


    public ActionsHandler(ConnectionRequest connectionRequest) {
        this.connectionRequest = connectionRequest;
        tamagotchiService = new TamagotchiService();
        historyActionService = new HistoryActionService();
    }

    public TamagotchiResponseBody handleEatAction(ActionRequest actionRequest) throws TamagotchiNotFoundException, InternalException {
        int id = getIdFromMessage();

        Tamagotchi tamagotchi = tamagotchiService.feedTamagotchi(id);
        HistoryAction historyAction = addHistoryAction(actionRequest.getMessageType());
        LoggerUtil.logTrace("tamagotchi eating");

        return new TamagotchiResponseBody(tamagotchi, historyAction);
    }

    public TamagotchiResponseBody handleSleepAction(ActionRequest actionRequest) throws TamagotchiNotFoundException, InternalException {
        int id = getIdFromMessage();

        Tamagotchi tamagotchi = tamagotchiService.putTamagotchiToSleep(id);
        HistoryAction historyAction = addHistoryAction(actionRequest.getMessageType());
        LoggerUtil.logTrace("tamagotchi sleeping");

        return new TamagotchiResponseBody(tamagotchi, historyAction);
    }


    public TamagotchiResponseBody handleAwakeAction(ActionRequest actionRequest) throws TamagotchiNotFoundException, InternalException {
        int id = getIdFromMessage();

        Tamagotchi tamagotchi = tamagotchiService.awakeTamagotchi(id);
        HistoryAction historyAction = addHistoryAction(actionRequest.getMessageType());
        LoggerUtil.logTrace("tamagotchi playing");

        return new TamagotchiResponseBody(tamagotchi, historyAction);
    }

    public TamagotchiResponseBody handlePlayAction(ActionRequest actionRequest) throws TamagotchiNotFoundException, InternalException {
        int id = getIdFromMessage();

        Tamagotchi tamagotchi = tamagotchiService.playWithTamagotchi(id);
        HistoryAction historyAction = addHistoryAction(actionRequest.getMessageType());
        LoggerUtil.logTrace("tamagotchi playing");

        return new TamagotchiResponseBody(tamagotchi, historyAction);
    }

//    public TamagotchiResponseBody handleNameAction(ActionRequest actionRequest) throws RequestException, InternalException {
//        String name = actionRequest.getBody();
//        if (name.isEmpty()) {
//            throw new RequestException("Name cannot be empty");
//        }
//
//        Tamagotchi tamagotchi = tamagotchiService.createTamagotchi(name);
//        HistoryAction historyAction = addHistoryAction(actionRequest.getMessageType());
//        LoggerUtil.logTrace("tamagotchi created: " + name);
//
//        return new TamagotchiResponseBody(tamagotchi, historyAction);
//    }

    public TamagotchiWithFullHistoryResponseBody handleGetAction(ActionRequest actionRequest) throws InternalException, TamagotchiNotFoundException {
        int id = getIdFromMessage();

        Tamagotchi tamagotchi = tamagotchiService.findTamagotchiById(id);
        addHistoryAction(actionRequest.getMessageType());
        LoggerUtil.logTrace("get tamagotchi: " + tamagotchi.toJSON());

        return new TamagotchiWithFullHistoryResponseBody(tamagotchi, historyActionService.getHistoryActionsForATamagotchi(id));
    }

    private int getIdFromMessage() {
        return connectionRequest.getId();
    }

    private HistoryAction addHistoryAction(ActionType actionType) throws InternalException {
        return historyActionService.createHistoryAction(connectionRequest.getOwner(), actionType, getIdFromMessage());
    }
}
