package martin.ufc.server.infra.request;

import martin.ufc.exception.InvalidMessageException;
import martin.ufc.exception.RequestException;
import martin.ufc.util.LoggerUtil;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class RequestFactory {
    private RequestFactory() {}
    public static ActionRequest createActionRequest(DataInputStream dataInputStream) throws RequestException {
        String request = readDataInputStream(dataInputStream);
        return buildRequestMessage(request);
    }

    public static ConnectionRequest createConnectionRequest(DataInputStream dataInputStream) throws RequestException {
        String request = readDataInputStream(dataInputStream);
        return buildConnectionRequestMessage(request);
    }

    private static ConnectionRequest buildConnectionRequestMessage(String request) throws RequestException {
        try {
            return new ConnectionRequest(request);
        } catch (InvalidMessageException invalidMessageException) {
            LoggerUtil.logError("Fail while reading request message format");
            throw new RequestException("invalid request message: " + invalidMessageException.getMessage());
        }
    }

    private static ActionRequest buildRequestMessage(String request) throws RequestException {
        try {
            return new ActionRequest(request);
        } catch (InvalidMessageException invalidMessageException) {
            LoggerUtil.logError("Fail while reading request message format");
            throw new RequestException("invalid request message: " + invalidMessageException.getMessage());
        }
    }

    private static String readDataInputStream(DataInputStream dataInputRequest) throws RequestException {
        try {
            return readInputStream(dataInputRequest);
        } catch (Exception e) {
            LoggerUtil.logError("Error while trying reading the request");
            throw new RequestException("Request not readable");
        }
    }

    private static String readInputStream(DataInputStream inputStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        return in.readLine();
    }
}
