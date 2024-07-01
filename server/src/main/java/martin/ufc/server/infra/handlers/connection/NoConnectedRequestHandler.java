package martin.ufc.server.infra.handlers.connection;

import martin.ufc.exception.InternalException;
import martin.ufc.exception.InvalidConnectionException;
import martin.ufc.exception.TamagotchiNotFoundException;
import martin.ufc.model.tamagotchi.Tamagotchi;
import martin.ufc.server.infra.handlers.ConnectedTamagotchisManager;
import martin.ufc.server.infra.handlers.CreateHandler;
import martin.ufc.server.infra.request.no_connected.ConnectionRequest;
import martin.ufc.server.infra.request.no_connected.CreationRequest;
import martin.ufc.server.infra.response.dto.Response;
import martin.ufc.server.infra.response.dto.ResponseBody;
import martin.ufc.server.infra.response.dto.TamagotchiWithFullHistoryResponseBody;
import martin.ufc.server.infra.services.HistoryActionService;
import martin.ufc.server.infra.services.TamagotchiService;

public class NoConnectedRequestHandler {
    private NoConnectedRequestHandler() {}

    public static Response connectTamagotchi(ConnectionRequest connectedRequest) throws TamagotchiNotFoundException, InvalidConnectionException, InternalException {
        if (ConnectedTamagotchisManager.checkTamagotchiIsAvailable(connectedRequest.getId())) {
            Tamagotchi tamagotchi = TamagotchiService.findTamagotchiById(connectedRequest.getId());
            TamagotchiWithFullHistoryResponseBody fullResponse = new TamagotchiWithFullHistoryResponseBody(tamagotchi, HistoryActionService.getHistoryActionsForATamagotchi(connectedRequest.getId()));
            return Response.createSuccessResponse(fullResponse);
        }

        throw new InvalidConnectionException("Tamagotchi is already being taken care of");
    }

    public static int createTamagotchi(CreationRequest creationRequest) throws InternalException {
        return CreateHandler.handleCreateAction(creationRequest);
    }
}
