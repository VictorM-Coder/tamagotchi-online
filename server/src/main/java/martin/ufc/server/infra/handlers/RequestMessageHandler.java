package martin.ufc.server.infra.handlers;

import martin.ufc.exception.*;
import martin.ufc.server.infra.request.RequestMessage;
import martin.ufc.server.infra.response.Response;
import martin.ufc.server.infra.response.body.ResponseBody;
import martin.ufc.util.LoggerUtil;

public class RequestMessageHandler {
    private final ActionsHandler actionsHandler;

    public RequestMessageHandler() {
        actionsHandler = new ActionsHandler();
    }

    public Response handleClientMessage(String request) {
        Response response;
        try {
            RequestMessage requestMessage = new RequestMessage(request);
            ResponseBody responseBody = executeAction(requestMessage);
            response = Response.createSuccessResponse(responseBody);

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

    private ResponseBody executeAction(RequestMessage requestMessage) throws TamagotchiNotCreatedException, TamagotchiNotFoundException, RequestException, InternalException {
        return switch (requestMessage.getMessageType()) {
            case EAT -> actionsHandler.handleEatAction(requestMessage);
            case SLEEP -> actionsHandler.handleSleepAction(requestMessage);
            case AWAKE -> actionsHandler.handleAwakeAction(requestMessage);
            case PLAY -> actionsHandler.handlePlayAction(requestMessage);
            case NAME -> actionsHandler.handleNameAction(requestMessage);
            case GET -> actionsHandler.handleGetAction(requestMessage);
            default -> {
                LoggerUtil.logError("Unknown Action");
                throw new RequestException("Unknown Action");
            }
        };
    }
}
