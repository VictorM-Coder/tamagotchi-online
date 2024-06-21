package martin.ufc.server.infra.handlers;

import martin.ufc.exception.InternalException;
import martin.ufc.exception.RequestException;
import martin.ufc.exception.TamagotchiNotFoundException;
import martin.ufc.server.infra.request.ActionRequest;
import martin.ufc.server.infra.request.ConnectionRequest;
import martin.ufc.server.infra.request.RequestFactory;
import martin.ufc.server.infra.response.Response;
import martin.ufc.server.infra.response.ResponseMessenger;
import martin.ufc.server.infra.response.body.ResponseBody;
import martin.ufc.util.LoggerUtil;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class ActionRequestsHandler implements Runnable {
    private DataInputStream dataInputStream;
    private OutputStream outputStream;
    private ActionsHandler actionsHandler;
    private final Socket client;
    private boolean connectionOn;


    public ActionRequestsHandler(Socket client) {
        this.client = client;
        setDataStreams();
    }

    @Override
    public void run() {
        connectWithClient();
        while (connectionOn) {
            handleRequest();
        }
        closeConnection();
    }

    private void handleRequest() {
        Response response;
        try {
            ActionRequest actionRequest = RequestFactory.createActionRequest(dataInputStream);
            ResponseBody responseBody = executeAction(actionRequest);
            response = Response.createSuccessResponse(responseBody);
            ResponseMessenger.sendResponse(outputStream, response);

        } catch (RequestException requestException) {
            response = Response.createErrorResponse(requestException);
            ResponseMessenger.sendResponse(outputStream, response);
        } catch (TamagotchiNotFoundException | InternalException exception) {
            response = Response.createFailResponse(exception);
            ResponseMessenger.sendResponse(outputStream, response);
        }


    }

    private ResponseBody executeAction(ActionRequest actionRequest) throws TamagotchiNotFoundException, InternalException {
        return switch (actionRequest.getMessageType()) {
            case EAT -> actionsHandler.handleEatAction(actionRequest);
            case SLEEP -> actionsHandler.handleSleepAction(actionRequest);
            case AWAKE -> actionsHandler.handleAwakeAction(actionRequest);
            case PLAY -> actionsHandler.handlePlayAction(actionRequest);
            //TODO refazer criação de tamagotchi
//            case NAME -> actionsHandler.handleNameAction(actionRequest);
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

    private void connectWithClient() {
        try {
            ConnectionRequest connectionRequest = RequestFactory.createConnectionRequest(dataInputStream);
            actionsHandler = new ActionsHandler(connectionRequest);
            connectionOn = true;
            LoggerUtil.logTrace(connectionRequest.getOwner() + " entered");
        } catch (RequestException requestException) {
            Response response = Response.createFailResponse(requestException);
            ResponseMessenger.sendResponse(outputStream, response);
        }
    }
}
