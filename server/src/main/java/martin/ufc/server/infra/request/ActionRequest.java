package martin.ufc.server.infra.request;

import martin.ufc.exception.InvalidMessageException;

public class ActionRequest {
    private final ActionType actionType;

    @Override
    public String toString() {
        return actionType + " ";
    }

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
