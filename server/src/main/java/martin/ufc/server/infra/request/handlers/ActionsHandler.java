package martin.ufc.server.infra.request.handlers;

import martin.ufc.exception.InternalException;
import martin.ufc.exception.RequestException;
import martin.ufc.exception.TamagotchiNotFoundException;
import martin.ufc.model.history.HistoryAction;
import martin.ufc.model.tamagotchi.Tamagotchi;
import martin.ufc.server.infra.request.message.RequestMessage;
import martin.ufc.server.infra.response.body.TamagotchiWithFullHistoryResponseBody;
import martin.ufc.server.infra.response.body.TamagotchiResponseBody;
import martin.ufc.server.infra.services.HistoryActionService;
import martin.ufc.server.infra.services.TamagotchiService;
import martin.ufc.util.LoggerUtil;

public class ActionsHandler {
    private final TamagotchiService tamagotchiService;
    private final HistoryActionService historyActionService;

    public ActionsHandler() {
        tamagotchiService = new TamagotchiService();
        historyActionService = new HistoryActionService();
    }

    public TamagotchiResponseBody handleEatAction(RequestMessage requestMessage) throws TamagotchiNotFoundException, RequestException, InternalException {
        int id = getIdFromMessage(requestMessage);

        Tamagotchi tamagotchi = tamagotchiService.feedTamagotchi(id);
        HistoryAction historyAction = addHistoryAction(requestMessage, id);
        LoggerUtil.logTrace("tamagotchi eating");

        return new TamagotchiResponseBody(tamagotchi, historyAction);
    }

    public TamagotchiResponseBody handleSleepAction(RequestMessage requestMessage) throws TamagotchiNotFoundException, RequestException, InternalException {
        int id = getIdFromMessage(requestMessage);

        Tamagotchi tamagotchi = tamagotchiService.putTamagotchiToSleep(id);
        HistoryAction historyAction = addHistoryAction(requestMessage, id);
        LoggerUtil.logTrace("tamagotchi sleeping");

        return new TamagotchiResponseBody(tamagotchi, historyAction);
    }


    public TamagotchiResponseBody handleAwakeAction(RequestMessage requestMessage) throws RequestException, TamagotchiNotFoundException, InternalException {
        int id = getIdFromMessage(requestMessage);

        Tamagotchi tamagotchi = tamagotchiService.awakeTamagotchi(id);
        HistoryAction historyAction = addHistoryAction(requestMessage, id);
        LoggerUtil.logTrace("tamagotchi playing");

        return new TamagotchiResponseBody(tamagotchi, historyAction);
    }

    public TamagotchiResponseBody handlePlayAction(RequestMessage requestMessage) throws TamagotchiNotFoundException, RequestException, InternalException {
        int id = getIdFromMessage(requestMessage);

        Tamagotchi tamagotchi = tamagotchiService.playWithTamagotchi(id);
        HistoryAction historyAction = addHistoryAction(requestMessage, id);
        LoggerUtil.logTrace("tamagotchi playing");

        return new TamagotchiResponseBody(tamagotchi, historyAction);
    }

    public TamagotchiResponseBody handleNameAction(RequestMessage requestMessage) throws RequestException, InternalException {
        String name = requestMessage.getBody();
        if (name.isEmpty()) {
            throw new RequestException("Name cannot be empty");
        }

        Tamagotchi tamagotchi = tamagotchiService.createTamagotchi(name);
        HistoryAction historyAction = addHistoryAction(requestMessage, tamagotchi.getId());
        LoggerUtil.logTrace("tamagotchi created: " + name);

        return new TamagotchiResponseBody(tamagotchi, historyAction);
    }

    public TamagotchiWithFullHistoryResponseBody handleGetAction(RequestMessage requestMessage) throws RequestException, InternalException, TamagotchiNotFoundException {
        int id = getIdFromMessage(requestMessage);

        Tamagotchi tamagotchi = tamagotchiService.findTamagotchiById(id);
        addHistoryAction(requestMessage, id);
        LoggerUtil.logTrace("get tamagotchi: " + tamagotchi.toJSON());

        return new TamagotchiWithFullHistoryResponseBody(tamagotchi, historyActionService.getHistoryActionsForATamagotchi(id));
    }

    private int getIdFromMessage(RequestMessage requestMessage) throws RequestException {
        try {
            return Integer.parseInt(requestMessage.getBody());
        } catch (Exception e) {
            throw new RequestException("Invalid id");
        }
    }

    private HistoryAction addHistoryAction(RequestMessage requestMessage, int id) throws InternalException {
        return historyActionService.createHistoryAction(requestMessage.getOwner(), requestMessage.getMessageType(), id);
    }
}
