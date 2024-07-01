package martin.ufc.server.infra.handlers;

import martin.ufc.exception.InternalException;
import martin.ufc.exception.TamagotchiNotFoundException;
import martin.ufc.model.history.HistoryAction;
import martin.ufc.model.tamagotchi.Tamagotchi;
import martin.ufc.server.infra.request.action.ActionRequest;
import martin.ufc.server.infra.request.no_connected.ConnectionRequest;
import martin.ufc.server.infra.response.dto.TamagotchiResponseBody;
import martin.ufc.server.infra.response.dto.TamagotchiWithFullHistoryResponseBody;
import martin.ufc.server.infra.services.HistoryActionService;
import martin.ufc.server.infra.services.TamagotchiService;
import martin.ufc.util.LoggerUtil;
public class ActionsHandler {
    private final ConnectionRequest connectionRequest;

    public ActionsHandler(ConnectionRequest connectionRequest) {
        this.connectionRequest = connectionRequest;
    }

    public TamagotchiResponseBody handleEatAction(ActionRequest actionRequest) throws TamagotchiNotFoundException, InternalException {
        Tamagotchi tamagotchi = TamagotchiService.feedTamagotchi(getIdFromMessage());
        LoggerUtil.logTrace("tamagotchi eating");

        return buildTamagotchiResponse(actionRequest, tamagotchi);
    }

    public TamagotchiResponseBody handleSleepAction(ActionRequest actionRequest) throws TamagotchiNotFoundException, InternalException {
        Tamagotchi tamagotchi = TamagotchiService.putTamagotchiToSleep(getIdFromMessage());
        LoggerUtil.logTrace("tamagotchi sleeping");

        return buildTamagotchiResponse(actionRequest, tamagotchi);
    }


    public TamagotchiResponseBody handleAwakeAction(ActionRequest actionRequest) throws TamagotchiNotFoundException, InternalException {
        Tamagotchi tamagotchi = TamagotchiService.awakeTamagotchi(getIdFromMessage());
        LoggerUtil.logTrace("tamagotchi wake up");

        return buildTamagotchiResponse(actionRequest, tamagotchi);
    }

    public TamagotchiResponseBody handlePlayAction(ActionRequest actionRequest) throws TamagotchiNotFoundException, InternalException {
        Tamagotchi tamagotchi = TamagotchiService.playWithTamagotchi(getIdFromMessage());
        LoggerUtil.logTrace("tamagotchi playing");

        return buildTamagotchiResponse(actionRequest, tamagotchi);
    }

    public TamagotchiWithFullHistoryResponseBody handleGetAction() throws InternalException, TamagotchiNotFoundException {
        Tamagotchi tamagotchi = TamagotchiService.findTamagotchiById(getIdFromMessage());
        LoggerUtil.logTrace("get tamagotchi: " + tamagotchi.toJSON());

        return new TamagotchiWithFullHistoryResponseBody(tamagotchi, HistoryActionService.getHistoryActionsForATamagotchi(getIdFromMessage()));
    }

    private TamagotchiResponseBody buildTamagotchiResponse(ActionRequest actionRequest, Tamagotchi tamagotchi) throws InternalException {
        HistoryAction historyAction = HistoryActionService.createHistoryAction(connectionRequest.getOwner(), actionRequest.getActionType().name(), getIdFromMessage());
        return new TamagotchiResponseBody(tamagotchi, historyAction);
    }

    private int getIdFromMessage() {
        return connectionRequest.getId();
    }
}
