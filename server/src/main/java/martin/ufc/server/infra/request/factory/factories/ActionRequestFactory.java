package martin.ufc.server.infra.request.factory.factories;

import martin.ufc.exception.RequestException;
import martin.ufc.server.infra.request.action.ActionRequest;
import martin.ufc.util.LoggerUtil;

public class ActionRequestFactory extends RequestFactory<ActionRequest> {
    @Override
    protected ActionRequest buildRequest(String request) throws RequestException {
        try {
            return new ActionRequest(request);
        } catch (RequestException requestException) {
            LoggerUtil.logError("Fail while reading request message format");
            throw new RequestException("invalid request message: " + requestException.getMessage());
        }
    }
}
