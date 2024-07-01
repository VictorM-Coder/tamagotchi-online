package martin.ufc.server.infra.handlers;

import martin.ufc.exception.InternalException;
import martin.ufc.model.tamagotchi.Tamagotchi;
import martin.ufc.server.infra.request.no_connected.CreationRequest;
import martin.ufc.server.infra.services.HistoryActionService;
import martin.ufc.server.infra.services.TamagotchiService;
import martin.ufc.util.LoggerUtil;

public class CreateHandler {

    private CreateHandler() {}

    public static int handleCreateAction(CreationRequest creationRequest) throws InternalException {
        String tamagotchiName = creationRequest.getTamagotchiName();

        Tamagotchi tamagotchi = TamagotchiService.createTamagotchi(tamagotchiName);

        HistoryActionService.createHistoryAction(
                creationRequest.getOwner(),
                creationRequest.getType().name(),
                tamagotchi.getId()
        );

        LoggerUtil.logTrace("tamagotchi created: " + tamagotchiName);

        return tamagotchi.getId();
    }
}