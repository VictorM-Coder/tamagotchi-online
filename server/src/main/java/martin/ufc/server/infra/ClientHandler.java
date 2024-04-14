package martin.ufc.server.infra;

import martin.ufc.exception.InvalidMessageException;
import martin.ufc.exception.TamagotchiNotCreatedException;
import martin.ufc.model.TamagotchiKeeper;
import martin.ufc.util.LoggerUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
    private Socket client;
    private TamagotchiKeeper tamagotchiKeeper;

    public ClientHandler(Socket client, TamagotchiKeeper tamagotchiKeeper) {
        this.client = client;
        this.tamagotchiKeeper = tamagotchiKeeper;
    }

    public void communicateWithClient() {
        try (
                DataInputStream dataInput = new DataInputStream(client.getInputStream());
                DataOutputStream dataOutput = new DataOutputStream(client.getOutputStream());
        ) {
            handleClientMessage(dataInput.readUTF());
            dataOutput.writeUTF(tamagotchiKeeper.getTamagotchi().toJSON());
        } catch (IOException | TamagotchiNotCreatedException e) {
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
        } catch (InvalidMessageException | TamagotchiNotCreatedException invalidMessageException) {
            LoggerUtil.logError(invalidMessageException.getMessage());
        }
    }

    private void executeAction(Message message) throws TamagotchiNotCreatedException {
        switch (message.getMessageType()) {
            case EAT:
                tamagotchiKeeper.feed();
                LoggerUtil.logInfo("tamagothi eating");
                break;
            case SLEEP:
                tamagotchiKeeper.putToSleep();
                LoggerUtil.logInfo("tamagothi sleeping");
                break;
            case PLAY:
                tamagotchiKeeper.putToPlay();
                LoggerUtil.logInfo("tamagothi playing");
                break;
            case NAME:
                tamagotchiKeeper.createTamagotchi(message.getBody());
                LoggerUtil.logInfo("tamagothi created: " + message.getBody());
                break;
            case GET:
                LoggerUtil.logInfo("get tamagothi: " + tamagotchiKeeper.getTamagotchi());
                break;
            default:
                LoggerUtil.logError("Ação não reconhecida");
                break;
        }
    }

}
