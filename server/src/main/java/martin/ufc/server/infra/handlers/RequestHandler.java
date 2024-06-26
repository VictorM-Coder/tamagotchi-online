package martin.ufc.server.infra.handlers;

import martin.ufc.exception.InternalException;
import martin.ufc.exception.RequestException;
import martin.ufc.exception.TamagotchiNotFoundException;
import martin.ufc.server.infra.request.DataInputStreamReader;
import martin.ufc.server.infra.request.action.ActionRequest;
import martin.ufc.server.infra.request.factory.RequestFactoryProvider;
import martin.ufc.server.infra.request.no_connected.ConnectionRequest;
import martin.ufc.server.infra.request.no_connected.CreationRequest;
import martin.ufc.server.infra.request.no_connected.NoConnectedRequest;
import martin.ufc.server.infra.request.types.NoConnectedRequestType;
import martin.ufc.server.infra.response.ResponseMessenger;
import martin.ufc.server.infra.response.dto.Response;
import martin.ufc.server.infra.response.dto.ResponseBody;
import martin.ufc.util.LoggerUtil;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

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
            handleRequest();
        }

        if (ownerConnected != null) {
            closeConnection();
        }
    }

    private void handleRequest() {
        Response response = null;
        try {
            String request = DataInputStreamReader.read(dataInputStream);
            ActionRequest actionRequest = RequestFactoryProvider.createActionRequest(request);
            ResponseBody responseBody = executeAction(actionRequest);
            response = Response.createSuccessResponse(responseBody);

        } catch (RequestException requestException) {
            response = Response.createErrorResponse(requestException);
        } catch (TamagotchiNotFoundException | InternalException exception) {
            response = Response.createFailResponse(exception);
        } finally {
            ResponseMessenger.sendResponse(outputStream, response);
        }


    }

    private ResponseBody executeAction(ActionRequest actionRequest) throws TamagotchiNotFoundException, InternalException {
        return switch (actionRequest.getActionType()) {
            case EAT -> actionsHandler.handleEatAction(actionRequest);
            case SLEEP -> actionsHandler.handleSleepAction(actionRequest);
            case AWAKE -> actionsHandler.handleAwakeAction(actionRequest);
            case PLAY -> actionsHandler.handlePlayAction(actionRequest);
            case GET -> actionsHandler.handleGetAction(actionRequest);
            case END -> endConnection();
        };
    }

    private ResponseBody endConnection() {
        connectionOn = false;
        return () -> "{message: end connection}";
    }

    private void closeConnection() {
        try {
            client.close();
            dataInputStream.close();
            outputStream.close();
            LoggerUtil.logTrace(ownerConnected + " left");
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

    private void handleNoConnectedRequests() {
        Response response = null;
        try {
            String request = DataInputStreamReader.read(dataInputStream);
            NoConnectedRequest noConnectedRequest = RequestFactoryProvider.createNoConnectedRequest(request);

            if (noConnectedRequest.getType().equals(NoConnectedRequestType.CONNECT)) {
                connect((ConnectionRequest) noConnectedRequest);
                ResponseBody responseBody = actionsHandler.handleGetAction(new ActionRequest("GET"));
                response  = Response.createSuccessResponse(responseBody);
            } else if (noConnectedRequest.getType().equals(NoConnectedRequestType.CREATE)) {
                ResponseBody responseBody = createTamagotchi((CreationRequest) noConnectedRequest);
                response  = Response.createSuccessResponse(responseBody);
            }

        } catch (RequestException requestException) {
            response = Response.createFailResponse(requestException);
        }  catch (InternalException e) {
            response = Response.createErrorResponse(e);
        } catch (Exception e) {
            response = Response.createErrorResponse(new InternalException("Unknown error"));
        }
        finally {
            ResponseMessenger.sendResponse(outputStream, response);
        }
    }

    private ResponseBody createTamagotchi(CreationRequest noConnectedRequest) throws InternalException {
        return CreateHandler.handleCreateAction(noConnectedRequest);
    }

    private void connect(ConnectionRequest noConnectedRequest) {
        ConnectionRequest connectionRequest = noConnectedRequest;
        actionsHandler = new ActionsHandler(connectionRequest);
        connectionOn = true;
        ownerConnected = connectionRequest.getOwner();
        LoggerUtil.logTrace(ownerConnected + " entered");
    }
}
