package martin.ufc.server.infra.handlers.connection;

import martin.ufc.exception.*;
import martin.ufc.server.infra.data_stream_handlers.RequestReader;
import martin.ufc.server.infra.handlers.ActionsHandler;
import martin.ufc.server.infra.request.action.ActionRequest;
import martin.ufc.server.infra.request.factory.RequestFactoryProvider;
import martin.ufc.server.infra.request.no_connected.ConnectionRequest;
import martin.ufc.server.infra.request.no_connected.CreationRequest;
import martin.ufc.server.infra.request.no_connected.NoConnectedRequest;
import martin.ufc.server.infra.data_stream_handlers.ResponseMessenger;
import martin.ufc.server.infra.request.types.NoConnectedRequestType;
import martin.ufc.server.infra.response.dto.Response;
import martin.ufc.server.infra.response.dto.ResponseBody;
import martin.ufc.util.LoggerUtil;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.TimeoutException;

public class ConnectionHandler implements Runnable {
    private DataInputStream dataInputStream;
    private OutputStream outputStream;
    private ActionsHandler actionsHandler;
    private final Socket client;
    private boolean connectionOn;
    private String ownerConnected;


    public ConnectionHandler(Socket client) {
        this.client = client;
        setDataStreams();
    }

    @Override
    public void run() {
        handleNoConnectedRequests();

        while (connectionOn) {
            handleActionRequests();
        }

        closeConnection();
    }

    private void handleActionRequests() {
        handleRequests(() -> {
            String request = readRequest();
            ActionRequest actionRequest = RequestFactoryProvider.createActionRequest(request);
            ResponseBody responseBody = executeActionRequest(actionRequest);
            return Response.createSuccessResponse(responseBody);
        });
    }

    private void handleNoConnectedRequests() {
        handleRequests(() -> {
            String request = readRequest();
            NoConnectedRequest noConnectedRequest = RequestFactoryProvider.createNoConnectedRequest(request);
            return executeNoConnectedRequest(noConnectedRequest);
        });
    }

    private void handleRequests(RequestProcessor processor) {
        Response response = null;
        try {
            response = processor.runProcess();
        } catch (RequestException requestException) {
            response = Response.createErrorResponse(requestException);
        } catch (TamagotchiNotFoundException | InternalException | InvalidConnectionException exception) {
            response = Response.createFailResponse(exception);
            connectionOn = false;
        } catch (TimeoutException e) {
            response = Response.createErrorResponse(new RequestException("Timeout"));
            connectionOn = false;
        } catch (Exception e) {
            e.printStackTrace();
            response = Response.createErrorResponse(new InternalException("Unknown error"));
        } finally {
            trySendMessageOrCloseConnection(response);
        }
    }

    private ResponseBody executeActionRequest(ActionRequest actionRequest) throws TamagotchiNotFoundException, InternalException {
        return switch (actionRequest.getActionType()) {
            case EAT -> actionsHandler.handleEatAction(actionRequest);
            case SLEEP -> actionsHandler.handleSleepAction(actionRequest);
            case AWAKE -> actionsHandler.handleAwakeAction(actionRequest);
            case PLAY -> actionsHandler.handlePlayAction(actionRequest);
            case GET -> actionsHandler.handleGetAction();
            case END -> endConnection();
        };
    }

    private Response executeNoConnectedRequest(NoConnectedRequest noConnectedRequest) throws InternalException, TamagotchiNotFoundException, InvalidConnectionException {
        return switch (noConnectedRequest.getType()) {
            case CONNECT -> connectToTamagotchi((ConnectionRequest) noConnectedRequest);
            case CREATE -> createTamagotchi(noConnectedRequest);
        };
    }

    private Response connectToTamagotchi(ConnectionRequest noConnectedRequest) throws TamagotchiNotFoundException, InvalidConnectionException, InternalException {
        Response response = NoConnectedRequestHandler.connectTamagotchi(noConnectedRequest);
        connect(noConnectedRequest);
        return response;
    }

    private Response createTamagotchi(NoConnectedRequest noConnectedRequest) throws InternalException, InvalidConnectionException, TamagotchiNotFoundException {
        CreationRequest creationRequest = (CreationRequest) noConnectedRequest;

        int tamagotchiId = NoConnectedRequestHandler.createTamagotchi(creationRequest);
        ConnectionRequest connectionRequest = new ConnectionRequest(
                NoConnectedRequestType.CONNECT,
                noConnectedRequest.getOwner(),
                tamagotchiId
        );

        return connectToTamagotchi(connectionRequest);
    }

    private void trySendMessageOrCloseConnection(Response response) {
        try {
            ResponseMessenger.sendResponse(outputStream, response);
        }  catch (WriteOutputStreamException e) {
            connectionOn = false;
        }
    }

    private String readRequest() throws TimeoutException, RequestException {
        return RequestReader.read(dataInputStream);
    }

    private ResponseBody endConnection() {
        connectionOn = false;
        return () -> "{\"message\": \"end connection\"}";
    }

    private void setDataStreams() {
        try {
            dataInputStream = new DataInputStream(client.getInputStream());
            outputStream = client.getOutputStream();
        } catch (IOException e) {
            LoggerUtil.logError("Internal error");
            connectionOn = false;
        }
    }

    private void connect(ConnectionRequest noConnectedRequest) {
        actionsHandler = new ActionsHandler(noConnectedRequest);
        connectionOn = true;
        ownerConnected = noConnectedRequest.getOwner();
        LoggerUtil.logTrace(ownerConnected + " entered");
    }

    private void closeConnection() {
        try {
            client.close();
            if (dataInputStream != null) dataInputStream.close();
            if (outputStream != null) outputStream.close();
            if (ownerConnected != null) LoggerUtil.logTrace(ownerConnected + " left");
        } catch (IOException e) {
            LoggerUtil.logError("Error while closing the client");
        }
    }
}
