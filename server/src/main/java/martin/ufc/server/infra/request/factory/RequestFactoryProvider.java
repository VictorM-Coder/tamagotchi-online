package martin.ufc.server.infra.request.factory;

import martin.ufc.exception.RequestException;
import martin.ufc.server.infra.request.action.ActionRequest;
import martin.ufc.server.infra.request.factory.factories.ActionRequestFactory;
import martin.ufc.server.infra.request.factory.factories.NoConnectedRequestFactory;
import martin.ufc.server.infra.request.no_connected.NoConnectedRequest;

public class RequestFactoryProvider {
    private static final ActionRequestFactory ACTION_REQUEST_FACTORY = new ActionRequestFactory();
    private static final NoConnectedRequestFactory NO_CONNECTED_REQUEST_FACTORY = new NoConnectedRequestFactory();

    public static NoConnectedRequest createNoConnectedRequest(String request) throws RequestException {
        return NO_CONNECTED_REQUEST_FACTORY.createRequest(request);
    }

    public static ActionRequest createActionRequest(String request) throws RequestException {
        return ACTION_REQUEST_FACTORY.createRequest(request);
    }
}
