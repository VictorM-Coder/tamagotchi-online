package martin.ufc.server.infra.request.factory.factories;

import martin.ufc.exception.RequestException;
import martin.ufc.server.infra.request.no_connected.ConnectionRequest;
import martin.ufc.server.infra.request.no_connected.CreationRequest;
import martin.ufc.server.infra.request.no_connected.NoConnectedRequest;
import martin.ufc.server.infra.request.no_connected.NoConnectedRequestType;

public class NoConnectedRequestFactory extends RequestFactory<NoConnectedRequest> {
    private static final int TYPE_INDEX = 0;
    private static final int OWNER_INDEX = 1;
    private static final int BODY_INDEX = 2;
    public NoConnectedRequestFactory() { }

    @Override
    protected NoConnectedRequest buildRequest(String request) throws RequestException {
        String[] splitMessage;
        splitMessage = request.split(" ");

        if (splitMessage.length == 3) {
            return buildRequestFromStringArray(splitMessage);
        } else {
            throw new RequestException("invalid request format");
        }
    }

    private NoConnectedRequest buildRequestFromStringArray(String[] splitMessage) throws RequestException {
        NoConnectedRequestType type = extractType(splitMessage);
        String owner = extractField(splitMessage, OWNER_INDEX, "No owner passed");

        if (type.equals(NoConnectedRequestType.CREATE)) {
            String tamagotchiName = extractField(splitMessage, BODY_INDEX, "No body passed");
            return new CreationRequest(type, owner, tamagotchiName);
        } else if (type.equals(NoConnectedRequestType.CONNECT)) {
            int id = extractId(splitMessage);
            return new ConnectionRequest(type, owner, id);
        } else {
            throw new RequestException("Request type not recognized");
        }
    }

    private static int extractId(String[] splitMessage) throws RequestException {
        try {
            return Integer.parseInt(splitMessage[BODY_INDEX]);
        } catch (NumberFormatException e) {
            throw new RequestException("invalid request message: Invalid id format");
        }
    }

    private NoConnectedRequestType extractType(String[] splitMessage) throws RequestException {
        try {
            return NoConnectedRequestType.valueOf(extractField(splitMessage, TYPE_INDEX, "No type passed"));
        } catch (IllegalArgumentException e) {
            throw new RequestException("Request type not recognized");
        }
    }

    private String extractField(String[] splitMessage, int bodyIndex, String exceptionMessage) throws RequestException {
        if (splitMessage[bodyIndex].isEmpty()) {
            throw new RequestException("invalid request message: " + exceptionMessage);
        }
        return splitMessage[bodyIndex];
    }
}
