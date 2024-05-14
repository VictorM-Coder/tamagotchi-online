package martin.ufc.server.infra;

import martin.ufc.exception.InvalidMessageException;
import martin.ufc.exception.TamagotchiNotCreatedException;
import martin.ufc.model.TamagotchiKeeper;
import martin.ufc.util.LoggerUtil;

import java.io.*;
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
                OutputStream dataOutput = client.getOutputStream();
        ) {
            handleClientMessage(readInputStream(dataInput));

            byte[] message = tamagotchiKeeper.getTamagotchi().toJSON().getBytes();
            dataOutput.write(message);
        } catch (IOException | TamagotchiNotCreatedException e) {
            LoggerUtil.logError(e.getMessage());
        }
    }

    public void closeClient() {
        try {
            client.close();
        } catch (IOException e) {
            LoggerUtil.logTrace(e.getMessage());
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
                LoggerUtil.logTrace("tamagothi eating");
                break;
            case SLEEP:
                tamagotchiKeeper.putToSleep();
                LoggerUtil.logTrace("tamagothi sleeping");
                break;
            case PLAY:
                tamagotchiKeeper.putToPlay();
                LoggerUtil.logTrace("tamagothi playing");
                break;
            case NAME:
                tamagotchiKeeper.createTamagotchi(message.getBody());
                LoggerUtil.logTrace("tamagothi created: " + message.getBody());
                break;
            case GET:
                LoggerUtil.logTrace("get tamagothi: " + tamagotchiKeeper.getTamagotchi());
                break;
            default:
                LoggerUtil.logError("Ação não reconhecida");
                break;
        }
    }

    private String readInputStream(DataInputStream inputStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        return in.readLine();
    }
}
