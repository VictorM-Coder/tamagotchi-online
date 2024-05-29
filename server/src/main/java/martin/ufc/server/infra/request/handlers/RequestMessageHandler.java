package martin.ufc.server.infra.request.handlers;

import martin.ufc.exception.*;
import martin.ufc.model.tamagotchi.Tamagotchi;
import martin.ufc.server.infra.request.message.RequestMessage;
import martin.ufc.server.infra.response.Response;
import martin.ufc.server.infra.services.HistoryActionService;
import martin.ufc.server.infra.services.TamagotchiService;
import martin.ufc.util.LoggerUtil;

public class RequestMessageHandler {
    private final TamagotchiService tamagotchiService;
    private final HistoryActionService historyActionService;
    public RequestMessageHandler() {
        this.tamagotchiService = new TamagotchiService();
        this.historyActionService = new HistoryActionService();
    }

    public Response handleClientMessage(String request) {
        Response response;
        try {
            RequestMessage requestMessage = new RequestMessage(request);
            Tamagotchi tamagotchi = executeAction(requestMessage);
            response = Response.createSuccessResponse(tamagotchi);

        } catch (InvalidMessageException invalidMessageException) {
            LoggerUtil.logError(invalidMessageException.getMessage());
            var errorRequest = new RequestException(invalidMessageException.getMessage());
            response = Response.createErrorResponse(errorRequest);

        } catch (TamagotchiNotCreatedException tamagotchiNotCreatedException) {
            LoggerUtil.logError(tamagotchiNotCreatedException.getMessage());
            var failRequest = new RequestException(tamagotchiNotCreatedException.getMessage());
            response = Response.createFailResponse(failRequest);

        } catch (RequestException requestException) {
            response = Response.createErrorResponse(requestException);

        } catch (TamagotchiNotFoundException | InternalException exception) {
            response = Response.createFailResponse(exception);
        }

        return response;
    }

    private Tamagotchi executeAction(RequestMessage requestMessage) throws TamagotchiNotCreatedException, TamagotchiNotFoundException, RequestException, InternalException {
        return switch (requestMessage.getMessageType()) {
            case EAT -> handleEatAction(requestMessage);
            case SLEEP -> handleSleepAction(requestMessage);
            case AWAKE -> handleAwakeAction(requestMessage);
            case PLAY -> handlePlayAction(requestMessage);
            case NAME -> handleNameAction(requestMessage);
            case GET -> handleGetAction(requestMessage);
            default -> {
                LoggerUtil.logError("Unknown Action");
                throw new RequestException("Unknown Action");
            }
        };
    }


    private int getIdFromMessage(RequestMessage requestMessage) throws RequestException {
        try {
            return Integer.parseInt(requestMessage.getBody());
        } catch (Exception e) {
            throw new RequestException("Invalid id");
        }
    }

    private Tamagotchi handleEatAction(RequestMessage requestMessage) throws TamagotchiNotFoundException, RequestException, InternalException {
        int id = getIdFromMessage(requestMessage);
        Tamagotchi tamagotchi = tamagotchiService.feedTamagotchi(id);
        historyActionService.createHistoryAction(requestMessage.getOwner(), requestMessage.getMessageType(), id);
        LoggerUtil.logTrace("tamagothi eating");

        return tamagotchi;
    }

    private Tamagotchi handleSleepAction(RequestMessage requestMessage) throws TamagotchiNotFoundException, RequestException, InternalException {
        int id = getIdFromMessage(requestMessage);
        Tamagotchi tamagotchi = tamagotchiService.putTamagotchiToSleep(id);
        historyActionService.createHistoryAction(requestMessage.getOwner(), requestMessage.getMessageType(), id);
        LoggerUtil.logTrace("tamagothi sleeping");

        return tamagotchi;
    }

    private Tamagotchi handleAwakeAction(RequestMessage requestMessage) throws RequestException, TamagotchiNotFoundException, InternalException {
        int id = getIdFromMessage(requestMessage);
        Tamagotchi tamagotchi = tamagotchiService.awakeTamagotchi(id);
        historyActionService.createHistoryAction(requestMessage.getOwner(), requestMessage.getMessageType(), id);
        LoggerUtil.logTrace("tamagothi playing");

        return tamagotchi;
    }

    private Tamagotchi handlePlayAction(RequestMessage requestMessage) throws TamagotchiNotFoundException, RequestException, InternalException {
        int id = getIdFromMessage(requestMessage);
        Tamagotchi tamagotchi = tamagotchiService.playWithTamagotchi(id);
        historyActionService.createHistoryAction(requestMessage.getOwner(), requestMessage.getMessageType(), id);
        LoggerUtil.logTrace("tamagothi playing");

        return tamagotchi;
    }

    private Tamagotchi handleNameAction(RequestMessage requestMessage) throws RequestException, InternalException {
        String name = requestMessage.getBody();
        if (name.isEmpty()) {
            throw new RequestException("Name cannot be empty");
        }

        Tamagotchi tamagotchi = tamagotchiService.createTamagotchi(name);
        historyActionService.createHistoryAction(requestMessage.getOwner(), requestMessage.getMessageType(), tamagotchi.getId());
        LoggerUtil.logTrace("tamagothi created: " + name);

        return tamagotchi;
    }

    private Tamagotchi handleGetAction(RequestMessage requestMessage) throws RequestException, InternalException, TamagotchiNotFoundException {
        int id = getIdFromMessage(requestMessage);
        Tamagotchi tamagotchi = tamagotchiService.findTamagotchiById(id);
        historyActionService.createHistoryAction(requestMessage.getOwner(), requestMessage.getMessageType(), id);
        LoggerUtil.logTrace("get tamagothi: " + tamagotchi.toJSON());

        return tamagotchi;
    }
}
