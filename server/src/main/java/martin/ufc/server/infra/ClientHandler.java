package martin.ufc.server.infra;

import martin.ufc.exception.InvalidMessageException;
import martin.ufc.util.LoggerUtil;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
    private Socket client;

    public ClientHandler(Socket client) {
        this.client = client;
    }

    public void communicateWithClient() {
        try (
                DataInputStream dataInput = new DataInputStream(client.getInputStream());
//                DataOutputStream dataOutput = new DataOutputStream(client.getOutputStream());
        ) {
            handleClientMessage(dataInput.readUTF());
        } catch (IOException e) {
            LoggerUtil.logError(e.getMessage());
        }
    }

    public void closeClient() {
        try {
            client.close();
        } catch (IOException e) {
            LoggerUtil.logInfo(e.getMessage());
        }
    }

    private void handleClientMessage(String clientMessage) {
        try {
            Message message = new Message(clientMessage);
            executeAction(message);
        } catch (InvalidMessageException invalidMessageException) {
            LoggerUtil.logError(invalidMessageException.getMessage());
        }
    }

    private void executeAction(Message message) {
        switch (message.getMessageType()) {
            case EAT:
                LoggerUtil.logInfo("tamagothi comendo");
                break;
            case SLEEP:
                LoggerUtil.logInfo("tamagothi dormindo");
                break;
            case PLAY:
                LoggerUtil.logInfo("tamagothi brincando");
                break;
            case NAME:
                LoggerUtil.logInfo("tamagothi recebendo novo nome: " + message.getBody());
                break;
            case GET:
                LoggerUtil.logInfo("tamagothi buscando: " + message.getBody());
                break;
            default:
                LoggerUtil.logError("Ação não reconhecida");
                break;
        }
    }

}
