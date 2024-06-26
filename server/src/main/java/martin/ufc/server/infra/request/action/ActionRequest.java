package martin.ufc.server.infra.request.action;

import martin.ufc.exception.RequestException;
import martin.ufc.server.infra.request.Request;
import martin.ufc.server.infra.request.types.ActionType;

public class ActionRequest implements Request {
    private final ActionType actionType;

    public ActionRequest(String message) throws RequestException {
        try {
            actionType = ActionType.valueOf(message);
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new RequestException("Invalid Request type");
        }
    }

    public ActionType getActionType() {
        return actionType;
    }
}
