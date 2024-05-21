package martin.ufc.server.infra.request.handlers;

import martin.ufc.exception.InvalidMessageException;
import martin.ufc.exception.RequestException;
import martin.ufc.exception.TamagotchiNotCreatedException;
import martin.ufc.model.tamagotchi.TamagotchiKeeper;
import martin.ufc.server.infra.request.message.Message;
import martin.ufc.server.infra.response.Response;
import martin.ufc.util.LoggerUtil;

public class RequestMessageHandler {
    private TamagotchiKeeper tamagotchiKeeper;
    public RequestMessageHandler(TamagotchiKeeper tamagotchiKeeper) {
        this.tamagotchiKeeper = tamagotchiKeeper;
    }

    public Response handleClientMessage(String request) {
        Response response;
        try {
            Message message = new Message(request);
            executeAction(message);
            response = Response.createSuccessResponse(tamagotchiKeeper.getTamagotchi());

        } catch (InvalidMessageException invalidMessageException) {
            LoggerUtil.logError(invalidMessageException.getMessage());
            var errorRequest = new RequestException(invalidMessageException.getMessage());
            response = Response.createErrorResponse(errorRequest);

        } catch (TamagotchiNotCreatedException tamagotchiNotCreatedException) {
            LoggerUtil.logError(tamagotchiNotCreatedException.getMessage());
            var failRequest = new RequestException(tamagotchiNotCreatedException.getMessage());
            response = Response.createFailResponse(failRequest);
        }

        return response;
    }

    private void executeAction(Message message) throws TamagotchiNotCreatedException {
        switch (message.getMessageType()) {
            case EAT:
                tamagotchiKeeper.feed();
                LoggerUtil.logTrace("tamagothi eating");
                break;
            case SLEEP:
                tamagotchiKeeper.putToSleep();
                LoggerUtil.logTrace("tamagothi sleeping");
                break;
            case PLAY:
                tamagotchiKeeper.putToPlay();
                LoggerUtil.logTrace("tamagothi playing");
                break;
            case NAME:
                tamagotchiKeeper.createTamagotchi(message.getBody());
                LoggerUtil.logTrace("tamagothi created: " + message.getBody());
                break;
            case GET:
                LoggerUtil.logTrace("get tamagothi: " + tamagotchiKeeper.getTamagotchi());
                break;
            default:
                LoggerUtil.logError("Ação não reconhecida");
                break;
        }
    }
}
