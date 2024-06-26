package martin.ufc.server.infra.request.factory.factories;

import martin.ufc.exception.RequestException;
import martin.ufc.server.infra.request.Request;

public abstract class RequestFactory<E extends Request> {
    public E createRequest(String request) throws RequestException {
        return buildRequest(request);
    }

    protected abstract E buildRequest(String request) throws RequestException;

}
