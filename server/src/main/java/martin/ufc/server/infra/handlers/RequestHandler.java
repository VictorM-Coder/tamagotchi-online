package martin.ufc.server.infra.handlers;

import martin.ufc.exception.InternalException;
import martin.ufc.exception.RequestException;
import martin.ufc.exception.TamagotchiNotFoundException;
import martin.ufc.server.infra.data_stream_handlers.RequestReader;
import martin.ufc.server.infra.request.action.ActionRequest;
import martin.ufc.server.infra.request.factory.RequestFactoryProvider;
import martin.ufc.server.infra.request.no_connected.ConnectionRequest;
import martin.ufc.server.infra.request.no_connected.CreationRequest;
import martin.ufc.server.infra.request.no_connected.NoConnectedRequest;
import martin.ufc.server.infra.request.types.NoConnectedRequestType;
import martin.ufc.server.infra.data_stream_handlers.ResponseMessenger;
import martin.ufc.server.infra.response.dto.Response;
import martin.ufc.server.infra.response.dto.ResponseBody;
import martin.ufc.util.LoggerUtil;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.TimeoutException;

public class RequestHandler implements Runnable {
    private DataInputStream dataInputStream;
    private OutputStream outputStream;
    private ActionsHandler actionsHandler;
    private final Socket client;
    private boolean connectionOn;
    private String ownerConnected;


    public RequestHandler(Socket client) {
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
        Response response = null;
        try {
            String request = readRequest();
            ActionRequest actionRequest = RequestFactoryProvider.createActionRequest(request);
            ResponseBody responseBody = executeActionRequest(actionRequest);
            response = Response.createSuccessResponse(responseBody);

        } catch (RequestException requestException) {
            response = Response.createErrorResponse(requestException);
        } catch (TamagotchiNotFoundException | InternalException exception) {
            response = Response.createFailResponse(exception);
            connectionOn = false;
        } catch (TimeoutException e) {
            response = Response.createErrorResponse(new RequestException("Timeout"));
            LoggerUtil.logError("Timeout");
            connectionOn = false;
        } catch (Exception e) {
            response = Response.createErrorResponse(new InternalException("Unknown error"));
        } finally {
            ResponseMessenger.sendResponse(outputStream, response);
        }
    }

    private void handleNoConnectedRequests() {
        Response response = null;
        try {
            String request = readRequest();
            NoConnectedRequest noConnectedRequest = RequestFactoryProvider.createNoConnectedRequest(request);
            response = executeNoConnectedRequest(noConnectedRequest);

        } catch (RequestException requestException) {
            response = Response.createErrorResponse(requestException);
        }  catch (InternalException | TamagotchiNotFoundException e) {
            response = Response.createFailResponse(e);
            connectionOn = false;
        } catch (TimeoutException e) {
            response = Response.createErrorResponse(new RequestException("Timeout"));
            LoggerUtil.logError("Timeout");
            connectionOn = false;
        } catch (Exception e) {
            response = Response.createErrorResponse(new InternalException("Unknown error"));
        } finally {
            ResponseMessenger.sendResponse(outputStream, response);
        }
    }

    private ResponseBody executeActionRequest(ActionRequest actionRequest) throws TamagotchiNotFoundException, InternalException {
        return switch (actionRequest.getActionType()) {
            case EAT -> actionsHandler.handleEatAction(actionRequest);
            case SLEEP -> actionsHandler.handleSleepAction(actionRequest);
            case AWAKE -> actionsHandler.handleAwakeAction(actionRequest);
            case PLAY -> actionsHandler.handlePlayAction(actionRequest);
            case GET -> actionsHandler.handleGetAction(actionRequest);
            case END -> endConnection();
        };
    }

    private Response executeNoConnectedRequest(NoConnectedRequest noConnectedRequest) throws InternalException, RequestException, TamagotchiNotFoundException {
        if (noConnectedRequest.getType().equals(NoConnectedRequestType.CONNECT)) {
            connect((ConnectionRequest) noConnectedRequest);
            ResponseBody responseBody = actionsHandler.handleGetAction(new ActionRequest("GET"));
            return Response.createSuccessResponse(responseBody);
        } else if (noConnectedRequest.getType().equals(NoConnectedRequestType.CREATE)) {
            ResponseBody responseBody = createTamagotchi((CreationRequest) noConnectedRequest);
            return Response.createSuccessResponse(responseBody);
        } else {
            throw new RequestException("Request type not recognized");
        }
    }

    private String readRequest() throws RequestException, TimeoutException {
        return RequestReader.read(dataInputStream);
    }

    private ResponseBody endConnection() {
        connectionOn = false;
        return () -> "{\"message\": \"end connection\"}";
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

    private void setDataStreams() {
        try {
            dataInputStream = new DataInputStream(client.getInputStream());
            outputStream = client.getOutputStream();
        } catch (IOException e) {
            LoggerUtil.logError("Internal error");
            connectionOn = false;
        }
    }

    private ResponseBody createTamagotchi(CreationRequest noConnectedRequest) throws InternalException {
        return CreateHandler.handleCreateAction(noConnectedRequest);
    }

    private void connect(ConnectionRequest noConnectedRequest) {
        actionsHandler = new ActionsHandler(noConnectedRequest);
        connectionOn = true;
        ownerConnected = noConnectedRequest.getOwner();
        LoggerUtil.logTrace(ownerConnected + " entered");
    }
}
