package martin.ufc.server.infra.request.handlers;

import martin.ufc.exception.InvalidMessageException;
import martin.ufc.exception.RequestException;
import martin.ufc.exception.TamagotchiNotFoundException;
import martin.ufc.exception.TamagotchiNotCreatedException;
import martin.ufc.model.tamagotchi.Tamagotchi;
import martin.ufc.server.infra.request.message.Message;
import martin.ufc.server.infra.response.Response;
import martin.ufc.server.infra.services.TamagotchiService;
import martin.ufc.util.LoggerUtil;

public class RequestMessageHandler {
    private final TamagotchiService tamagotchiService;
    public RequestMessageHandler() {
        this.tamagotchiService = new TamagotchiService();
    }

    public Response handleClientMessage(String request) {
        Response response;
        try {
            Message message = new Message(request);
            Tamagotchi tamagotchi = executeAction(message);
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

        } catch (TamagotchiNotFoundException tamagotchiNotFoundException) {
            response = Response.createFailResponse(tamagotchiNotFoundException);
        }

        return response;
    }

    private Tamagotchi executeAction(Message message) throws TamagotchiNotCreatedException, TamagotchiNotFoundException, RequestException {
        return switch (message.getMessageType()) {
            case EAT -> handleEatAction(message);
            case SLEEP -> handleSleepAction(message);
            case PLAY -> handlePlayAction(message);
            case NAME -> handleNameAction(message);
            case GET -> handleGetAction(message);
            default -> {
                LoggerUtil.logError("Unknown Action");
                throw new RequestException("Unknown Action");
            }
        };
    }

    private int getIdFromMessage(Message message) throws RequestException {
        try {
            return Integer.parseInt(message.getBody());
        } catch (Exception e) {
            throw new RequestException("Invalid id");
        }
    }

    private Tamagotchi handleEatAction(Message message) throws TamagotchiNotFoundException, RequestException {
        int id = getIdFromMessage(message);
        Tamagotchi tamagotchi = tamagotchiService.feedTamagotchi(id);
        LoggerUtil.logTrace("tamagothi eating");

        return tamagotchi;
    }

    private Tamagotchi handleSleepAction(Message message) throws TamagotchiNotFoundException, RequestException {
        int id = getIdFromMessage(message);
        Tamagotchi tamagotchi = tamagotchiService.putTamagotchiToSleep(id);
        LoggerUtil.logTrace("tamagothi sleeping");

        return tamagotchi;
    }

    private Tamagotchi handlePlayAction(Message message) throws TamagotchiNotFoundException, RequestException {
        int id = getIdFromMessage(message);
        Tamagotchi tamagotchi = tamagotchiService.playWithTamagotchi(id);
        LoggerUtil.logTrace("tamagothi playing");

        return tamagotchi;
    }

    private Tamagotchi handleNameAction(Message message) throws RequestException {
        String name = message.getBody();
        if (name.isEmpty()) {
            throw new RequestException("Name cannot be empty");
        }

        Tamagotchi tamagotchi = tamagotchiService.createTamagotchi(name);
        LoggerUtil.logTrace("tamagothi created: " + name);

        return tamagotchi;
    }

    private Tamagotchi handleGetAction(Message message) throws RequestException {
        int id = getIdFromMessage(message);
        Tamagotchi tamagotchi = tamagotchiService.findTamagotchiById(id);
        LoggerUtil.logTrace("get tamagothi: " + tamagotchi.toJSON());

        return tamagotchi;
    }
}
