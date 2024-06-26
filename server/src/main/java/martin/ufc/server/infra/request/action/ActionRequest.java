package martin.ufc.server.infra.request.action;

import martin.ufc.exception.InvalidMessageException;
import martin.ufc.server.infra.request.Request;

public class ActionRequest implements Request {
    private final ActionType actionType;

    public ActionRequest(String message) throws InvalidMessageException {
        try {
            actionType = ActionType.valueOf(message);
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new InvalidMessageException("Invalid MessageType");
        }
    }

    public ActionType getMessageType() {
        return actionType;
    }
}
