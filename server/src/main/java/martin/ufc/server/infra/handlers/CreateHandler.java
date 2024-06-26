package martin.ufc.server.infra.handlers;

import martin.ufc.exception.InternalException;
import martin.ufc.model.history.HistoryAction;
import martin.ufc.model.tamagotchi.Tamagotchi;
import martin.ufc.server.infra.request.no_connected.CreationRequest;
import martin.ufc.server.infra.response.dto.TamagotchiResponseBody;
import martin.ufc.server.infra.services.HistoryActionService;
import martin.ufc.server.infra.services.TamagotchiService;
import martin.ufc.util.LoggerUtil;

public class CreateHandler {

    private CreateHandler() {}

    public static TamagotchiResponseBody handleCreateAction(CreationRequest creationRequest) throws InternalException {
        String tamagotchiName = creationRequest.getTamagotchiName();

        Tamagotchi tamagotchi = TamagotchiService.createTamagotchi(tamagotchiName);

        HistoryAction historyAction = HistoryActionService.createHistoryAction(
                creationRequest.getOwner(),
                creationRequest.getType().toString(),
                tamagotchi.getId()
        );

        LoggerUtil.logTrace("tamagotchi created: " + tamagotchiName);

        return new TamagotchiResponseBody(tamagotchi, historyAction);
    }
}